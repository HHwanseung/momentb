package com.days.momentb.miniboard.repository;

import com.days.momentb.miniboard.entity.MiniBoard;
import com.days.momentb.miniboard.entity.Reply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insert200(){
        IntStream.rangeClosed(1,200).forEach(i->{
            Long mbNo = (long)(200 - (i%5));

            int replyCount = (i % 5);

            IntStream.rangeClosed(0,replyCount).forEach(j->{

                MiniBoard miniBoard = MiniBoard.builder().mbNo(mbNo).build();

                Reply reply = Reply.builder()
                        .mbReContent("Reply...")
                        .mbReWriter("mbReWriter...")
                        .miniBoard(miniBoard)
                        .build();
                replyRepository.save(reply);



            });

        });
    }


    @Test
    public void testBymbNo(){
        Long mbNo = 200L;
        List<Reply> replyList
                =replyRepository.findReplyByMiniBoard_MbNoOrderByMbReNo(mbNo);
        replyList.forEach(reply -> log.info(reply));
    }

    @Transactional
    @Test
    public void testRead(){
        Long rn = 1L;
        Reply reply = replyRepository.findById(rn).get();
        log.info(reply);
        log.info(reply.getMiniBoard());
    }

    @Test
    public void testListOfBoard(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("mbReNo").descending());
        Page<Reply> result = replyRepository.getListByMbNo(197L,pageable);
        log.info(result.getTotalElements());
        result.get().forEach(reply->log.info(reply));
    }

    @Test
    public void testCountOfBoard(){
        Long mbNo = 196L;
        log.info(replyRepository.getReplyCountOfMiniBoard(mbNo));
        int count = replyRepository.getReplyCountOfMiniBoard(mbNo);
        int lastPage = (int)(Math.ceil(count/10.0));

        if(lastPage ==0){
            lastPage =1;
        }

        Pageable pageable = PageRequest.of(lastPage-1, 10);
        Page<Reply> result = replyRepository.getListByMbNo(mbNo, pageable);

        log.info("total:"+result.getTotalElements());
        log.info("..."+result.getTotalPages());

        result.get().forEach(reply->{
            log.info(reply);
        });


    }

}
