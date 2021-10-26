package com.days.momentb.miniboard.repository.search;

import com.days.momentb.miniboard.entity.MiniBoard;
import com.days.momentb.miniboard.entity.QMiniBoard;
import com.days.momentb.miniboard.entity.QReply;
import com.days.momentb.personalboard.entity.QPersonalBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

import static com.days.momentb.miniboard.entity.QMiniBoard.miniBoard;

@Log4j2
public class MiniBoardSearchImpl extends QuerydslRepositorySupport implements MiniBoardSearch {

    public MiniBoardSearchImpl(){
        super(MiniBoard.class);
    }


    @Override
    public Page<MiniBoard> search1(char[] typeArr, String keyword, Pageable pageable) {
        log.info("----search1");
        QMiniBoard qMiniBoard = QMiniBoard.miniBoard;

        JPQLQuery<MiniBoard> jpqlQuery = from(qMiniBoard);



        if(typeArr!= null&&typeArr.length>0) {
            BooleanBuilder condition = new BooleanBuilder();

            for (char type : typeArr) {
                if (type == 'T') {
                    condition.or(qMiniBoard.mbContent.contains(keyword));
                } else if (type == 'W') {
                    condition.or(qMiniBoard.mbWriter.contains(keyword));
                } else if (type == 'C') {
                    condition.or(qMiniBoard.mbContent.contains(keyword));
                }
            }

            jpqlQuery.where(condition);
        }

            jpqlQuery.where(miniBoard.mbNo.gt(0L));

            JPQLQuery<MiniBoard> pagingQuery =
                    this.getQuerydsl().applyPagination(pageable, jpqlQuery);


        List<MiniBoard> miniBoardList = pagingQuery.fetch();
        long totalCount = pagingQuery.fetchCount();

        return new PageImpl<>(miniBoardList, pageable, totalCount);

    }

    @Override
    public Page<Object[]> searchWithReplyCount(char[] typeArr, String keyword, Pageable pageable) {
        QMiniBoard qMiniBoard = QMiniBoard.miniBoard;
        QReply qReply = QReply.reply;

        JPQLQuery<MiniBoard> query = from(qMiniBoard);
        query.leftJoin(qReply).on(qReply.miniBoard.eq(qMiniBoard));
        query.groupBy(qMiniBoard);

        if(typeArr != null&& typeArr.length >0){
            BooleanBuilder condition = new BooleanBuilder();

            for(char type:typeArr){
                if(type == 'T'){
                    condition.or(qMiniBoard.mbTitle.contains(keyword));
                }else if(type == 'C'){
                    condition.or(qMiniBoard.mbContent.contains(keyword));
                }else if (type =='W'){
                    condition.or(qMiniBoard.mbWriter.contains(keyword));
                }
            }
            query.where(condition);
        }
    query.where(qMiniBoard.mbNo.gt(0L));

        JPQLQuery<Tuple> selectQuery = query.select(qMiniBoard.mbNo, qMiniBoard.mbTitle, qMiniBoard.mbWriter,
                                        qMiniBoard.mbRegDate, qReply.count()) ;

        this.getQuerydsl().applyPagination(pageable, selectQuery);

        List<Tuple> tupleList = selectQuery.fetch();

        long totalCount = selectQuery.fetchCount();

        log.info(selectQuery);

        List<Object[]> arr = tupleList.stream().map(tuple-> tuple.toArray()).collect(Collectors.toList());

                return new PageImpl<>(arr, pageable, totalCount);
    }
}
