package com.days.momentb.personalboard.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="uuid")
public class PersonalBoardPictureDTO {

    private String uuid;

    private String fileName;

    private String savePath;

    private String imageLabel;

    private boolean selfDrawing;

    public String getLink(){
        return savePath+"/s_"+uuid+"_"+fileName;
    }

    public String getHandWritingLink(){
        return savePath+"/h_"+uuid+"_"+fileName;
    }





}
