package com.days.momentb.personalboard.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of="pbName")
public class PersonalBoardLocation {

    private String pbName;

    private String pbAddress;

    private String pbLat;

    private String pbLng;




}

