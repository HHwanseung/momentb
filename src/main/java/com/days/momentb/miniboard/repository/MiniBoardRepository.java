package com.days.momentb.miniboard.repository;

import com.days.momentb.miniboard.entity.MiniBoard;
import com.days.momentb.miniboard.repository.search.MiniBoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MiniBoardRepository extends JpaRepository<MiniBoard, Long>, MiniBoardSearch {


    @Query("select b.mbNo, b.mbTitle, b.mbWriter, b.mbRegDate, count(r) from MiniBoard b left join Reply r on r.miniBoard = b group by b")
    Page<Object[]> ex1(Pageable pageable);

}
