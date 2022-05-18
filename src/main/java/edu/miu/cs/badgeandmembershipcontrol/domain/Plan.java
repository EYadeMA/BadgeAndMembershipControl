package edu.miu.cs.badgeandmembershipcontrol.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.*;

@Data
@EqualsAndHashCode
@Entity
@RequiredArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated
    @ElementCollection
    @ToString.Include
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name="plan_location", joinColumns = {@JoinColumn(name="plan_id")},inverseJoinColumns = {@JoinColumn(name="location_id")})
    private List<Location> locations = new ArrayList<>();


    private Long counter;

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public void removeLocation(Location location) {
        this.locations = this.locations.stream().filter(loc -> !loc.getId().equals(location.getId())).toList();
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Plan plan = (Plan) o;
//        return Objects.equals(id, plan.id) && Objects.equals(name, plan.name) && Objects.equals(description, plan.description) && Objects.equals(roles, plan.roles) && Objects.equals(locations, plan.locations) && Objects.equals(counter, plan.counter);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, description, roles, locations, counter);
//    }
}
