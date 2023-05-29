package com.days.momentb.member.entity;


import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member {

    @Id
    private String mid;

    private String mpw;

    private String mname;


    @ElementCollection(fetch = FetchType.LAZY)
    private Set<MemberRole> roleSet;

    public void changePassword(String password){
        this.mpw = password;
    }
}

