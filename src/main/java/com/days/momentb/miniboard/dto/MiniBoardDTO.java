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
public class MiniBoardDTO {

    private Long mbNo;

    private String mbTitle;

    private String mbContent;

    private String mbWriter;

    private LocalDateTime mbRegDate;

    private LocalDateTime mbModDate;



}
