package com.days.momentb.miniboard.service;

import com.days.momentb.common.dto.PageRequestDTO;
import com.days.momentb.common.dto.PageResponseDTO;
import com.days.momentb.miniboard.dto.ReplyDTO;
import com.days.momentb.miniboard.entity.Reply;
import com.days.momentb.miniboard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ModelMapper modelMapper;
    private final ReplyRepository replyRepository;

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long mbNo, PageRequestDTO pageRequestDTO) {
        log.info(mbNo);
        log.info(pageRequestDTO);

        Pageable pageable = null;

        if(pageRequestDTO.getPage() == -1){
            int lastPage = calcLastPage(mbNo, pageRequestDTO.getSize());
            if(lastPage<=0){
                lastPage =1;
            }
            pageRequestDTO.setPage(lastPage);
        }
        pageable = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize() );



        Page<Reply> result = replyRepository.getListByMbNo(mbNo,pageable);

        log.info(result);
        List<ReplyDTO> dtoList = result.get().map(reply->
                        modelMapper.map(reply,ReplyDTO.class))
                .collect(Collectors.toList());
        log.info(dtoList);
          dtoList.forEach(replyDTO->log.info(replyDTO));

        return new PageResponseDTO<>(pageRequestDTO,(int)result.getTotalElements(),dtoList);
    }

    @Override
    public Long register(ReplyDTO replyDTO) {

        Reply reply = modelMapper.map(replyDTO, Reply.class);
      //  reply.getMiniBoard();
       // log.info(reply);
        log.info("replyserviceimpl로그..."+reply);
        log.info("replyServiceimpl로그...2"+reply.getMiniBoard());
        replyRepository.save(reply);
        return reply.getMbReNo();
    }

    @Override
    public PageResponseDTO<ReplyDTO> remove(Long mbNo, Long mbReNo, PageRequestDTO pageRequestDTO) {

        replyRepository.deleteById(mbReNo);

        return getListOfBoard(mbNo, pageRequestDTO);

    }

    private int calcLastPage(Long mbNo, double size){
        int count = replyRepository.getReplyCountOfMiniBoard(mbNo);
        int lastPage = (int)(Math.ceil(count/size));

        return lastPage;
    }

}
