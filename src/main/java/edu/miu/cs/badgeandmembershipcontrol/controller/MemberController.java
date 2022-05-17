package edu.miu.cs.badgeandmembershipcontrol.controller;

import com.sun.istack.NotNull;
import edu.miu.cs.badgeandmembershipcontrol.domain.Member;
import edu.miu.cs.badgeandmembershipcontrol.domain.Membership;
import edu.miu.cs.badgeandmembershipcontrol.domain.Plan;
import edu.miu.cs.badgeandmembershipcontrol.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    @NotNull private final MemberService memberService;


    @GetMapping()
    public ResponseEntity<?> getMembers(){
        List<Member> memberList = memberService.getAllMembers();
        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }

    @GetMapping(path = "/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable String memberId){
        Member member = memberService.getMember(Long.parseLong(memberId));

        if(member == null){
            return new ResponseEntity<String>("No Member Found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Member>(member, HttpStatus.OK);
    }

    @GetMapping(path = "/{memberId}/memberships")
    public ResponseEntity<?> getMembershipsByMember(@PathVariable String memberId){
        List<Membership> memberships = memberService.getMembershipsByMemberId(Long.parseLong(memberId));

        if(memberships.isEmpty()){
            return new ResponseEntity<String>("No Membership Found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Membership>>(memberships, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createMember(@RequestBody Member member){
        Member newMember = memberService.createMember(member);
        return new ResponseEntity<Member>(newMember, HttpStatus.OK);
    }

    @PutMapping(path = "/{memberId}")
    public ResponseEntity<?> updateMember(@PathVariable String memberId, @RequestBody Member member){
        if(memberService.getMember(Long.parseLong(memberId)) == null) {
            return new ResponseEntity<String>("No member by the Id " + memberId + " found", HttpStatus.NOT_FOUND);
        }
        Member updatedMember = memberService.updateMember(Long.parseLong(memberId), member);
        return new ResponseEntity<Member>(updatedMember, HttpStatus.OK);
    }

    @PostMapping(path = "/renewBadge/{memberId}")
    public ResponseEntity<?> renewMemberBadge(@PathVariable String memberId){
        if(memberService.getMember(Long.parseLong(memberId)) == null) {
            return new ResponseEntity<String>("No member by the Id " + memberId + " found", HttpStatus.NOT_FOUND);
        }
        Member member = memberService.createNewBadge(Long.parseLong(memberId));
        return new ResponseEntity<Member>(member, HttpStatus.OK);
    }

    @PostMapping(path = "/invokeMembership")
    public ResponseEntity<?> invokeMembership(@PathParam("memberId") String memberId, @PathParam("membershipId") String membershipId){
        Member member = memberService.deActivateMembership(Long.parseLong(memberId),Long.parseLong(membershipId));
        return new ResponseEntity<Member>(member, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable String memberId){
        if(!memberService.removeMember(Long.parseLong(memberId))){
            return new ResponseEntity<String>("No Member Found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }

}
