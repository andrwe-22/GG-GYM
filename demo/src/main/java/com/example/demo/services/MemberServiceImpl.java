package com.example.demo.services;

import com.example.demo.dto.JoinRequestDTO;
import com.example.demo.model.Member;
import com.example.demo.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member join(JoinRequestDTO request) {
        Member member = new Member();
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setMembershipPackage(request.getMembershipPackage());
        // Save the member to the database
        return memberRepository.save(member);
    }

    public boolean emailExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> login(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }
}
