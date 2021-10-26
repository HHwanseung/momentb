package com.days.momentb.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponseDTO<E> {

    //현재 페이지 번호
    private int page;
    //페이지당 게시물의 수
    private int size;

    //전체 데이터의 수
    private int count;

    //시작 페이지 번호
    private int start;
    // 끝 페이지 번호
    private int end;

    //이전 페이지 여부
    private boolean prev;
    //다음 페이지 여부
    private boolean next;

    //결과 데이터
    private List<E> dtoList;


    public PageResponseDTO(PageRequestDTO pageRequestDTO, int count){
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();
        this.count = count;
        //임시 end   3/10.0 = 0.3 => 1 * 10 => 10
        int tempEnd = (int)(Math.ceil(page/(double)size)) * 10;

        this.start = tempEnd - 9;
        this.prev = start > 1;

        if(this.start < 1){ this.start = 1; }

        //진짜 끝 페이지 count = 70 이고 tempEnd  10이라면
        if(tempEnd * size >= count ) {
            this.end = (int)(Math.ceil(count/(double)size));
            this.next = false;
        }else {
            this.end = tempEnd;
            this.next = true;
        }
    }

    public PageResponseDTO(PageRequestDTO pageRequestDTO, int count, List<E> dtoList){
        this(pageRequestDTO, count);
        this.dtoList = dtoList;
    }


}