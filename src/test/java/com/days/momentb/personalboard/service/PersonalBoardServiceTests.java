package com.days.momentb.personalboard.service;

import com.days.momentb.personalboard.dto.PersonalBoardDTO;
import com.days.momentb.personalboard.repository.PersonalBoardRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class PersonalBoardServiceTests {

    @Autowired
    private PersonalBoardService personalBoardService;

    @Test
    public void testModify(){
        PersonalBoardDTO personalBoardDTO = PersonalBoardDTO.builder().pbNo(200L).pbContent("gg")
                .build();

        personalBoardService.modify(personalBoardDTO);
    }

    @Test
    public void testRegister(){

        IntStream.rangeClosed(1,200).forEach(i->{
            PersonalBoardDTO personalBoardDTO = PersonalBoardDTO.builder()
                    .pbContent("content..."+i)
                    .memId("user"+(i%10))
                    .build();

            Long pbNo = personalBoardService.register(personalBoardDTO);
            log.info("pbNo:" + pbNo);
        });

    }

}
