package edu.miu.cs.badgeandmembershipcontrol.domain;

import lombok.Data;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    private Member member;
    @ManyToOne
    private Plan plan;

}
