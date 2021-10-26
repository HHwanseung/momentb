package com.days.momentb.personalboard.repository.search;

import com.days.momentb.personalboard.entity.PersonalBoard;
import com.days.momentb.personalboard.entity.QPersonalBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.days.momentb.personalboard.entity.QPersonalBoard.personalBoard;

@Log4j2
public class PersonalBoardSearchImpl extends QuerydslRepositorySupport implements PersonalBoardSearch {

    public PersonalBoardSearchImpl(){
        super(PersonalBoard.class);
    }


    @Override
    public Page<PersonalBoard> search1(char[] typeArr, String keyword, Pageable pageable) {
        log.info("----search1");
        QPersonalBoard qPersonalBoard = QPersonalBoard.personalBoard;

        JPQLQuery<PersonalBoard> jpqlQuery = from(qPersonalBoard);



        if(typeArr!= null&&typeArr.length>0) {
            BooleanBuilder condition = new BooleanBuilder();

            for (char type : typeArr) {
                if (type == 'T') {
                    condition.or(qPersonalBoard.pbContent.contains(keyword));
                } else if (type == 'I') {
                    condition.or(qPersonalBoard.memId.contains(keyword));
                }
            }

            jpqlQuery.where(condition);
        }

            jpqlQuery.where(personalBoard.pbNo.gt(0L));

            JPQLQuery<PersonalBoard> pagingQuery =
                    this.getQuerydsl().applyPagination(pageable, jpqlQuery);


        List<PersonalBoard> personalBoardList = pagingQuery.fetch();
        long totalCount = pagingQuery.fetchCount();

        return new PageImpl<>(personalBoardList, pageable, totalCount);

    }
}
