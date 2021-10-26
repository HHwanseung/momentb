package com.days.momentb.miniboard.repository;

import com.days.momentb.miniboard.dto.MiniBoardDTO;
import com.days.momentb.miniboard.entity.MiniBoard;
import com.days.momentb.personalboard.dto.PersonalBoardDTO;
import com.days.momentb.personalboard.entity.PersonalBoard;
import com.days.momentb.personalboard.repository.PersonalBoardRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@SpringBootTest
@Log4j2
public class MiniBoardRepositoryTests {


    @Autowired
    private MiniBoardRepository miniBoardRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Test
    public void testSearch1(){
        char[] typeArr=null;
        String keyword=null;
        Pageable pageable = PageRequest.of(0,10, Sort.by("mbNo").descending());
        Page<MiniBoard> result = miniBoardRepository.search1(typeArr, keyword, pageable);

        result.get().forEach(miniBoard ->
        {
            log.info(miniBoard);
            log.info("-----");

            MiniBoardDTO miniBoardDTO = modelMapper.map(miniBoard, MiniBoardDTO.class);

          log.info(miniBoardDTO);

        });

    }

    @Test
    public void testEx1(){

        Pageable pageable = PageRequest.of(1,10,Sort.by("mbNo").descending());
        Page<Object[]> result = miniBoardRepository.ex1(pageable);
        log.info(result);

        result.get().forEach(element->{
            Object[] arr = (Object[])element;
            log.info(Arrays.toString(arr));
        });

    }

    @Test
    public void testSearchWithReplyCount(){
        char[] typeArr = {'T'};
        String keyword = "10";

        Pageable pageable = PageRequest.of(0,10,Sort.by("mbNo").descending());

        Page<Object[]> result = miniBoardRepository.searchWithReplyCount(typeArr, keyword, pageable);

        result.get().forEach(arr->
                log.info(Arrays.toString(arr)));

    }

}
