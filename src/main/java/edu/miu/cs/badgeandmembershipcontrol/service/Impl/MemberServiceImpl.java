package edu.miu.cs.badgeandmembershipcontrol.service.Impl;

import edu.miu.cs.badgeandmembershipcontrol.domain.Member;
import edu.miu.cs.badgeandmembershipcontrol.repository.MemberRepository;
import edu.miu.cs.badgeandmembershipcontrol.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMember(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if(memberOptional.isPresent()){
            return memberOptional.get();
        }
        return null;
    }

    @Override
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Long memberId, Member member) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if(memberOptional.isPresent()){
            return memberRepository.save(member);
        }
        return null;
    }

    @Override
    public boolean removeMember(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if(memberOptional.isPresent()){
            memberRepository.deleteById(memberId);
            return true;
        }
        return false;
    }
}