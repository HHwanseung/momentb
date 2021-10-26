package com.days.momentb.personalboard.repository;

import com.days.momentb.personalboard.dto.PersonalBoardDTO;
import com.days.momentb.personalboard.entity.PersonalBoard;
import com.days.momentb.personalboard.service.PersonalBoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class PersonalBoardRepositoryTests {


    @Autowired
    private PersonalBoardService personalBoardService;

    @Autowired
    private PersonalBoardRepository personalBoardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{

            Set<String> tags = IntStream.rangeClosed(1,3).mapToObj(j->i+"_tag"+j).collect(Collectors.toSet());

            PersonalBoard personalBoard = PersonalBoard.builder()
                    .pbContent("sample....."+i)
                    .memId("user"+i)
                    .tags(tags)
                    .build();

            personalBoardRepository.save(personalBoard);

        });

    }

    @Transactional
    @Test
    public void testSelectOne(){
        Long pbNo = 1L;

        Optional<PersonalBoard> optionalPersonalBoard = personalBoardRepository.findById(pbNo);

        PersonalBoard personalBoard = optionalPersonalBoard.orElseThrow();

        log.info(personalBoard);

    }


    @Transactional
    @Test
    public void testPaging1(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("pbNo").descending());

        Page<PersonalBoard> result = personalBoardRepository.findAll(pageable);

        result.get().forEach(personalBoard->{
            log.info(personalBoard);
            log.info(personalBoard.getTags());
            log.info("-----");
        });

    }


    @Test
    public void testRegister(){
        List<String> tags = IntStream.rangeClosed(1,3).mapToObj(j->"_tag"+j).collect(Collectors.toList());

        PersonalBoardDTO dto = PersonalBoardDTO.builder()
                .pbContent("content...")
                .memId("user222")
                .tags(tags)
                .build();

        personalBoardService.register(dto);

    }

    @Test
    public void testSelectOne2(){
        Long pbNo = 1L;

        Optional<PersonalBoard> optionalPersonalBoard = personalBoardRepository.findById(pbNo);

        PersonalBoard personalBoard = optionalPersonalBoard.orElseThrow();

        PersonalBoardDTO dto = modelMapper.map(personalBoard, PersonalBoardDTO.class);

        log.info(dto);

    }

    @Transactional(readOnly = true)
    @Test
    public void read(){
        Long pbNo = 1L;
        PersonalBoardDTO dto = personalBoardService.read(pbNo);
        log.info(dto);
    }

    @Test
    public void testSearch1(){
        char[] typeArr=null;
        String keyword=null;
        Pageable pageable = PageRequest.of(0,10, Sort.by("pbNo").descending());
        Page<PersonalBoard> result = personalBoardRepository.search1(typeArr, keyword, pageable);

        result.get().forEach(personalBoard ->
        {
            log.info(personalBoard);
            log.info("-----");

            PersonalBoardDTO personalBoardDTO = modelMapper.map(personalBoard, PersonalBoardDTO.class);

            log.info(personalBoardDTO);

        });

    }

}
