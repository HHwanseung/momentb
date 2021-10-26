package com.days.momentb.personalboard.service;

import com.days.momentb.common.dto.PageRequestDTO;
import com.days.momentb.common.dto.PageResponseDTO;
import com.days.momentb.personalboard.dto.PersonalBoardDTO;
import com.days.momentb.personalboard.entity.PersonalBoard;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PersonalBoardService {


    Long register(PersonalBoardDTO personalBoardDTO);

    PageResponseDTO<PersonalBoardDTO> getList(PageRequestDTO pageRequestDTO);

    PersonalBoardDTO read(Long pbNo);

    void modify(PersonalBoardDTO personalBoardDTO);

    void delete(Long pbNo);

}
