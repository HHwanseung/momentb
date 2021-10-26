package com.days.momentb.miniboard.repository.search;

import com.days.momentb.miniboard.entity.MiniBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MiniBoardSearch {

    Page<MiniBoard> search1(char[] typeArr, String keyword, Pageable pageable);

    Page<Object[]> searchWithReplyCount(char[] typeArr, String keyword, Pageable pageable);


}
