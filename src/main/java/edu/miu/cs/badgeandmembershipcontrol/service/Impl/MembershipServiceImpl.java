package edu.miu.cs.badgeandmembershipcontrol.service.Impl;

import com.sun.istack.NotNull;
import edu.miu.cs.badgeandmembershipcontrol.domain.*;
import edu.miu.cs.badgeandmembershipcontrol.repository.MembershipRepository;
import edu.miu.cs.badgeandmembershipcontrol.service.MemberService;
import edu.miu.cs.badgeandmembershipcontrol.service.MembershipService;
import edu.miu.cs.badgeandmembershipcontrol.service.PlanService;
import edu.miu.cs.badgeandmembershipcontrol.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
//@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    @NotNull private final MembershipRepository membershipRepository;


    @NotNull private final PlanService planService;


    @NotNull private final MemberService memberService;

    @NotNull private final LocationServiceImpl locationService;


    @NotNull private final TransactionService transactionService;

    MembershipServiceImpl(MembershipRepository membershipRepository, PlanService planService, MemberService memberService,LocationServiceImpl locationService,@Lazy TransactionService transactionService){
        this.membershipRepository = membershipRepository;
        this.planService = planService;
        this.memberService = memberService;
        this.locationService = locationService;
        this.transactionService = transactionService;
    }

    @Override public List<Membership> getMemberMemberships(Long memberId) {
        return membershipRepository.findMembershipByMember_Id(memberId).orElse(null);
    }

    @Override public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    @Override public Membership getMemberShip(Long membershipId) {
        return membershipRepository.findById(membershipId).orElse(null);
    }


    // Get Member and Plan and set it to Membership then it saves it
    @Override public Membership createMemberShip(Membership membership) {
        Member member = memberService.getMember(membership.getMember().getId());
        Plan plan = planService.getPlan(membership.getPlan().getId());
        Optional<List<Membership>> optionalMemberships = getMembershipsByMemberIdAndPlanId(member.getId(), plan.getId(),"Active");
        if(optionalMemberships.get().size() > 0) return null;
        membership.setMember(member);
        membership.setPlan(plan);
        return membershipRepository.save(membership);
    }

    @Override public Membership updateMembership(Long membershipId, Membership membership) {
        Optional<Membership> membershipOptional = membershipRepository.findById(membershipId);
        if(membershipOptional.isPresent()){
            return membershipRepository.save(membership);
        }
        return null;
    }

    @Override public boolean removeMembership(Long membershipId) {
        Optional<Membership> membershipOptional = membershipRepository.findById(membershipId);
        if(membershipOptional.isPresent()){
            membershipRepository.deleteById(membershipId);
            return true;
        }
        return false;
    }

    @Override
    public Membership getMembershipByIdAndMemberId(Long membershipId, Long memberId) {
        return membershipRepository.findMembershipByIdAndMember_Id(membershipId,memberId).orElse(null);
    }

    @Override
    public Membership deActivateMembership(Long membershipId, Long memberId) {
        Membership membership = getMembershipByIdAndMemberId(membershipId,memberId);
        if(membership == null) return null;

        membership.deActivateMembership();
        return membershipRepository.save(membership);
    }

    @Override
    public Optional<List<Membership>> getMembershipsByMemberIdAndPlanId(Long memberId, Long planId,String status) {
        return membershipRepository.findMembershipsByMember_IdAndPlan_IdAndMembershipStatus(memberId,planId,status);
    }

    @Override
    public boolean checkAccess(Long memberId, Long locationId,LocationType locationType) {
        boolean checkMembership = false;
        TransactionType transactionType  = TransactionType.DECLINED;

        Transaction transaction = new Transaction();
        Location location = new Location();
        location.setId(locationId);
        transaction.setTransactionLoc(location);
        Badge badge = memberService.getActiveBadgeByMember(memberId);
        transaction.setBadge(badge);

        Optional<Membership> optionalMembership = findMembershipByMemberIdAndLocationIdAndStatus(memberId,locationId,"Active",locationType);

        if(optionalMembership.isPresent()){
            transaction.setMembership(optionalMembership.get());
            if(validateLocationTimeSlot(locationId)){
                checkMembership = true;
                transactionType = TransactionType.ALLOWED;
            }
        }
        transaction.setTransactionType(transactionType);

        transactionService.createTransaction(transaction);
        return checkMembership;
    }

    @Override
    public List<Membership> getMembershipsByMemberId(Long memberId) {
        return membershipRepository.findMembershipsByMember_Id(memberId).orElse(null);
    }

    @Override
    public Optional<Membership> findMembershipByMemberIdAndLocationIdAndStatus(Long memberId, Long locationId, String status, LocationType locationType) {
        return locationService.findMembershipByMemberIdAndLocationId(memberId,locationId,"Active");
    }

    private boolean validateLocationTimeSlot(Long locationId){
        Location location = locationService.getLocation(locationId);
        return location.checkTimeSlot();
    }


}
