package com.days.momentb.miniboard.service;

import com.days.momentb.miniboard.dto.MiniBoardDTO;
import com.days.momentb.personalboard.dto.PersonalBoardDTO;
import com.days.momentb.personalboard.service.PersonalBoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MiniBoardServiceTests {

    @Autowired
    private MiniBoardService miniBoardService;

    @Test
    public void testRegister(){

        IntStream.rangeClosed(1,200).forEach(i->{
            MiniBoardDTO miniBoardDTO = MiniBoardDTO.builder()
                    .mbContent("content..."+i)
                    .mbWriter("user"+(i%10))
                    .mbTitle("title..."+i)
                    .build();

            Long mbNo = miniBoardService.register(miniBoardDTO);
            log.info("mbNo:" + miniBoardDTO.getMbNo());
        });

    }

}
