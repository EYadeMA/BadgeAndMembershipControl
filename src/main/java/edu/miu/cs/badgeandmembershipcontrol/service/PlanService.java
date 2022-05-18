package edu.miu.cs.badgeandmembershipcontrol.service;

import java.util.List;

import edu.miu.cs.badgeandmembershipcontrol.domain.Location;
import edu.miu.cs.badgeandmembershipcontrol.domain.Plan;
import org.springframework.stereotype.Service;

@Service
public interface PlanService {

    List<Plan> getAllPlans();

    Plan getPlan(Long planId);

    List<Plan> getLocationPlans(Long locationId);

    List<Location> getPlanLocations(Long planId);

    Plan createPlan(Plan plan);

    Plan updatePlan(Long planId, Plan plan);

//    List<Membership> findPlanByMemberShip(Long membershipId);

    boolean removePlan(Long planId);

    List<Plan> findPlanByMember_Id(Long memberId);

    Plan findPlanByMemberShip(Long membershipId);

    Plan addLocationToPlan(Long planId, Location location);

    Plan removeLocationToPlan(Long planId, Location location);
}

