package com.days.momentb.personalboard.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="personal_board")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"tags","pictures","locations"})
public class PersonalBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pbNo;

    private String pbContent;

    @CreationTimestamp
    private LocalDateTime pbRegDate;

    @UpdateTimestamp
    private LocalDateTime pbModDate;

    private String memId;

    public void change(String content){
        this.pbContent = pbContent;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="personal_board_tags")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    @Builder.Default
    private Set<String> tags = new HashSet<>();


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="personal_board_picture")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    private Set<PersonalBoardPicture> pictures;



    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="personal_board_location")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    private Set<PersonalBoardLocation> locations;





}
