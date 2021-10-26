package com.days.momentb.personalboard.repository;

import com.days.momentb.personalboard.entity.PersonalBoard;
import com.days.momentb.personalboard.repository.search.PersonalBoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalBoardRepository extends JpaRepository<PersonalBoard, Long>, PersonalBoardSearch {

}
