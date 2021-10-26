package com.days.momentb.personalboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalBoardDTO {

    private Long pbNo;

    private String pbContent;

    private LocalDateTime pbRegDate;

    private LocalDateTime pbModDate;

    private String memId;

    private List<String> tags;



}
