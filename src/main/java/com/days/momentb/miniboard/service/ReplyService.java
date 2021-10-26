package com.days.momentb.miniboard.service;

import com.days.momentb.common.dto.PageRequestDTO;
import com.days.momentb.common.dto.PageResponseDTO;
import com.days.momentb.miniboard.dto.ReplyDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReplyService {

    PageResponseDTO<ReplyDTO> getListOfBoard (Long mbNo, PageRequestDTO pageRequestDTO);

    Long register(ReplyDTO replyDTO);

    PageResponseDTO<ReplyDTO> remove (Long mbNo, Long mbReNo, PageRequestDTO pageRequestDTO);


}
