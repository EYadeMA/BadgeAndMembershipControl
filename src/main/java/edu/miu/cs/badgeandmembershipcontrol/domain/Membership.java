package edu.miu.cs.badgeandmembershipcontrol.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.ToString;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@EqualsAndHashCode
public class Membership implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String membershipStatus = "Active";

    private Long counter;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;


    @Enumerated(EnumType.ORDINAL)
    private MembershipType membershipType;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Member member;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Plan plan;


    public void deActivateMembership(){
        this.membershipStatus = "InActive";
    }


}
