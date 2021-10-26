package com.days.momentb.miniboard.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MiniBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mbNo;

    private String mbTitle;

    private String mbContent;

    private String mbWriter;

    @CreationTimestamp
    private LocalDateTime mbRegDate;

    @UpdateTimestamp
    private LocalDateTime mbModDate;


    public void change(String mbContent, String mbTitle){
        this.mbContent = mbContent;
        this.mbTitle = mbTitle;
    }



}
