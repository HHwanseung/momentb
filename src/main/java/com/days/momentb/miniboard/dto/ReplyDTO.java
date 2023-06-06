package com.days.momentb.miniboard.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long mbReNo;

    private String mbReContent;

    private String mbReWriter;

    private Long mbNo;

    private LocalDateTime mbReRegDate;

    private LocalDateTime mbReModDate;

    private Long originReNo;

    private Long reDepth;

}
