package edu.miu.cs.badgeandmembershipcontrol.repository;

import edu.miu.cs.badgeandmembershipcontrol.domain.Location;
import edu.miu.cs.badgeandmembershipcontrol.domain.LocationType;
import edu.miu.cs.badgeandmembershipcontrol.domain.Membership;
import edu.miu.cs.badgeandmembershipcontrol.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {

    Optional<List<Location>> findLocationByLocationType(LocationType locationType);

    Optional<Location> findLocationByName(String name);

    @Query("select mm from Membership mm join mm.member m join mm.plan p join p.locations l where mm.membershipStatus =:status and m.id =:memberId and l.id =:locationId")
    Optional<Membership> findMembershipByStatusAndMemberIdAndLocationId(String status, Long memberId, Long locationId);

}
