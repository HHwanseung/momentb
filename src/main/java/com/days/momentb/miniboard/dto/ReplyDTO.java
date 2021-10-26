package com.days.momentb.miniboard.dto;

import com.days.momentb.miniboard.entity.MiniBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
