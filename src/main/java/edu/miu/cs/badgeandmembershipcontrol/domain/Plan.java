package edu.miu.cs.badgeandmembershipcontrol.domain;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Data
@EqualsAndHashCode
@Entity
@RequiredArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
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

}
