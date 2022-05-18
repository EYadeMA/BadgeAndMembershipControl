package edu.miu.cs.badgeandmembershipcontrol.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;


@Data
@Entity
@EqualsAndHashCode
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Column(unique = true)
    private String name;
    private String description;
    private int capacity;

    @Enumerated
    private LocationType locationType;

    @JsonBackReference(value="plans")
    @ManyToMany(mappedBy = "locations",fetch = FetchType.LAZY)
    private List<Plan> plans = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id")
    private List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();

    @Transient
    public boolean checkTimeSlot(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        boolean isValid = false;
        for (int i = 0; i<timeSlots.size(); i++){
            if(currentDateTime.isAfter(this.timeSlots.get(i).getStartTime()) && currentDateTime.isBefore(this.timeSlots.get(i).getEndTime()))
            {
                isValid = true;
                break;
            }
        }
        return isValid;
    }

//    @Override public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Location location = (Location) o;
//        return name.equals(location.name) && description.equals(location.description) && locationType.equals(location.locationType);
//    }
//
//    @Override public int hashCode() {
//        return Objects.hash(name, description);
//    }

}
