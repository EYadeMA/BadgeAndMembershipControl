package edu.miu.cs.badgeandmembershipcontrol.service;

import edu.miu.cs.badgeandmembershipcontrol.domain.Location;
import edu.miu.cs.badgeandmembershipcontrol.domain.LocationType;
import edu.miu.cs.badgeandmembershipcontrol.domain.Membership;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LocationService {
    List<Location> getAllLocations();

    Location getLocation(Long locationId);

    Location createLocation(Location location);

    Location updateLocation(Long locationId, Location location);

    List<Location> getLocationsByLocationType(LocationType locationType);

    boolean removeLocation(Long locationId);

    Optional<Membership> findMembershipByMemberIdAndLocationId(Long memberId, Long locationId, String status);
}
