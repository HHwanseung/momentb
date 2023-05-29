package com.days.momentb.personalboard.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="pbName")
public class PersonalBoardLocationDTO {

    private String pbName;

    private String pbAddress;

    private String pbLat;

    private String pbLng;

}

