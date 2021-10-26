package com.days.momentb.personalboard.repository.search;

import com.days.momentb.personalboard.entity.PersonalBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonalBoardSearch {

    Page<PersonalBoard> search1(char[] typeArr, String keyword, Pageable pageable);



}
