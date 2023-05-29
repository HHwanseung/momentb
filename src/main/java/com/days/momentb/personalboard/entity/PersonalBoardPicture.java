package com.days.momentb.personalboard.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of="uuid")
public class PersonalBoardPicture {
    private String uuid;

    private String fileName;

    private String savePath;

    private String imageLabel;

    private boolean selfDrawing;


}
