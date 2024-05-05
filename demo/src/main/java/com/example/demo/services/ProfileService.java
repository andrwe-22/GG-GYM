package com.example.demo.services;

import com.example.demo.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final MemberServiceImpl memberService;

    @Autowired
    public ProfileService(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    public Optional<Member> getMemberProfile(String email) {
        return memberService.login(email);
    }
}
