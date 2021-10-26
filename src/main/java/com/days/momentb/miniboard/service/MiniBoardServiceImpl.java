package com.days.momentb.miniboard.service;

import com.days.momentb.common.dto.PageRequestDTO;
import com.days.momentb.common.dto.PageResponseDTO;
import com.days.momentb.miniboard.dto.MiniBoardDTO;
import com.days.momentb.miniboard.dto.MiniBoardListDTO;
import com.days.momentb.miniboard.entity.MiniBoard;
import com.days.momentb.miniboard.repository.MiniBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class MiniBoardServiceImpl implements MiniBoardService {


    private final ModelMapper modelMapper;
    private final MiniBoardRepository miniBoardRepository;

    @Override
    public Long register(MiniBoardDTO miniBoardDTO) {
        //1.dto->vo
        MiniBoard miniBoard = modelMapper.map(miniBoardDTO, MiniBoard.class);
        //2.insert
        miniBoardRepository.save(miniBoard);
        return miniBoard.getMbNo();
    }

    @Override
    public PageResponseDTO<MiniBoardDTO> getList(PageRequestDTO pageRequestDTO) {

        char[] typeArr = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("mbNo").descending());

        Page<MiniBoard> result = miniBoardRepository.search1(typeArr, keyword, pageable);

        List<MiniBoardDTO> dtoList = result.get().map(
                        miniBoard -> modelMapper.map(miniBoard, MiniBoardDTO.class))
                .collect(Collectors.toList());
        long totalCount = result.getTotalElements();

        return new PageResponseDTO<>(pageRequestDTO, (int)totalCount, dtoList);

    }

    @Override
    public MiniBoardDTO read(Long mbNo) {

        Optional<MiniBoard> result = miniBoardRepository.findById(mbNo);

        if(result.isEmpty()){
            throw new RuntimeException("NOT FOUND");
        }

        return modelMapper.map(result.get(), MiniBoardDTO.class);

    }

    @Override
    public void modify(MiniBoardDTO miniBoardDTO) {

        Optional<MiniBoard> result = miniBoardRepository.findById(miniBoardDTO.getMbNo());

        if(result.isEmpty()){
            throw new RuntimeException("NOT FOUND");
        }

        MiniBoard miniBoard = result.get();
        miniBoard.change(miniBoardDTO.getMbContent(), miniBoardDTO.getMbTitle());

        miniBoardRepository.save(miniBoard);


    }

    @Override
    public void delete(Long mbNo) {

        miniBoardRepository.deleteById(mbNo);
    }

    @Override
    public PageResponseDTO<MiniBoardListDTO> getListWithReply(PageRequestDTO pageRequestDTO) {
        char[] typeArr = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("bno").descending());

        Page<Object[]> result = miniBoardRepository.searchWithReplyCount(typeArr, keyword, pageable);

        List<MiniBoardListDTO> dtoList = result.get().map(objects->{

            MiniBoardListDTO listDTO = MiniBoardListDTO.builder()//연관관계가 많을때 추천되는방법임.
                    .mbNo((Long)objects[0])
                    .mbTitle((String)objects[1])
                    .mbWriter((String)objects[2])
                    .mbRegDate((LocalDateTime)objects[3])
                    .mbReCount((Long)objects[4])
                    .build();

            return listDTO;
        }).collect(Collectors.toList());

        //boardList가 배열 이어서, 매칭이 안되서 modelmapper는 못쓰므로 노가다가 필요함

        return new PageResponseDTO<>(pageRequestDTO, (int)result.getTotalElements(), dtoList);
    }
}
