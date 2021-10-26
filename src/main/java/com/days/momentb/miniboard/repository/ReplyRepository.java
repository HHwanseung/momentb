package com.days.momentb.miniboard.repository;

import com.days.momentb.miniboard.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findReplyByMiniBoard_MbNoOrderByMbReNo(Long mbNo);

    @Query("select r from Reply r where r.miniBoard.mbNo =:mbNo")
    Page<Reply> getListByMbNo(Long mbNo, Pageable pageable);

    @Query("select count(r) from Reply r where r.miniBoard.mbNo = :mbNo")
    int getReplyCountOfMiniBoard(Long mbNo);

}
