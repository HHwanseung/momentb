package com.days.momentb.miniboard.service;

import com.days.momentb.common.dto.PageRequestDTO;
import com.days.momentb.common.dto.PageResponseDTO;
import com.days.momentb.miniboard.dto.MiniBoardDTO;
import com.days.momentb.miniboard.dto.MiniBoardListDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MiniBoardService {


    Long register(MiniBoardDTO miniBoardDTO);

    PageResponseDTO<MiniBoardDTO> getList(PageRequestDTO pageRequestDTO);

    MiniBoardDTO read(Long mbNo);

    void modify(MiniBoardDTO miniBoardDTO);

    void delete(Long mbNo);

    PageResponseDTO<MiniBoardListDTO> getListWithReply(PageRequestDTO pageRequestDTO);

}
