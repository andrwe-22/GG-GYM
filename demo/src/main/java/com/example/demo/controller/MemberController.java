package com.example.demo.controller;

import com.example.demo.config.JWTGenerator;
import com.example.demo.dto.MemberDTO;
import com.example.demo.model.Faculty;
import com.example.demo.model.Member;
import com.example.demo.model.Schedule;
import com.example.demo.model.Trainer;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberServiceImpl memberService;
    private final ProfileService profileService;

    private final TrainerService trainerService;

    private final ScheduleService scheduleService;

    private final FacultyService facultyService;

    private final JWTGenerator tokenGenerator;

    @Autowired
    public MemberController(MemberServiceImpl memberService, ProfileService profileService, TrainerService trainerService, ScheduleService scheduleService, FacultyService facultyService, JWTGenerator tokenGenerator) {
        this.memberService = memberService;
        this.profileService = profileService;
        this.trainerService = trainerService;
        this.scheduleService = scheduleService;
        this.facultyService = facultyService;
        this.tokenGenerator = tokenGenerator;
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Optional<Member> member = memberService.getMemberById(id);
        return member.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody MemberDTO memberDTO) {
        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setMembershipPackage(memberDTO.getMembershipPackage());

        Member savedMember = memberService.saveMember(member);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        Optional<Member> optionalMember = memberService.getMemberById(id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setName(memberDTO.getName());
            member.setEmail(memberDTO.getEmail());
            member.setMembershipPackage(memberDTO.getMembershipPackage());

            Member updatedMember = memberService.saveMember(member);
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Member> register(@RequestBody MemberDTO memberDTO) {
        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setMembershipPackage(memberDTO.getMembershipPackage());

        Member savedMember = memberService.saveMember(member);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO) {
        Optional<Member> member = memberService.login(memberDTO.getEmail());
        if (member.isPresent()) {
            String token = tokenGenerator.generateToken(member.get().getEmail()); // Use the updated method
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


//    @GetMapping("/profile/{email}")
//    public ResponseEntity<Member> getProfile(@PathVariable String email) {
//        Optional<Member> member = profileService.getMemberProfile(email);
//        return member.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("/profile/{email}")
//    public ResponseEntity<Member> updateProfile(@PathVariable String email, @RequestBody MemberDTO memberDTO) {
//        Optional<Member> optionalMember = memberService.login(email);
//        if (optionalMember.isPresent()) {
//            Member member = optionalMember.get();
//            member.setName(memberDTO.getName());
//            member.setEmail(memberDTO.getEmail());
//            member.setMembershipPackage(memberDTO.getMembershipPackage());
//            // Set associations with Schedule, Faculty, Trainer here
//
//            Member updatedMember = memberService.saveMember(member);
//            return ResponseEntity.ok(updatedMember);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<Member> getProfile(@PathVariable String email) {
        Optional<Member> member = memberService.getMemberByEmail(email);
        return member.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/profile/{email}")
    public ResponseEntity<Member> updateProfile(@PathVariable String email, @RequestBody MemberDTO memberDTO) {
        Optional<Member> optionalMember = memberService.getMemberByEmail(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setName(memberDTO.getName());
            member.setEmail(memberDTO.getEmail());
            member.setMembershipPackage(memberDTO.getMembershipPackage());
            // Set associations with Schedule, Faculty, Trainer here

            Member updatedMember = memberService.saveMember(member);
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/profile")
//    public ResponseEntity<MemberDTO> getProfile(Principal principal) {
//        String email = principal.getName(); // Email is the username in this context
//        Optional<Member> member = memberService.getMemberByEmail(email);
//        return member.map(value -> ResponseEntity.ok(new MemberDTO(value.getName(), value.getEmail(), value.getMembershipPackage())))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(Principal principal, @RequestBody MemberDTO memberDTO) {
        Optional<Member> memberOpt = memberService.getMemberByEmail(principal.getName());
        if (!memberOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Member member = memberOpt.get();

        // Check if the trainerId is not null and then set the new trainer
        if (memberDTO.getTrainerId() != null) {
            Trainer trainer = trainerService.getTrainerById(memberDTO.getTrainerId())
                    .orElse(null); // handle case where trainer might not exist
            member.setTrainer(trainer);
        }

        // Check if the scheduleId is not null and then set the new schedule
        if (memberDTO.getScheduleId() != null) {
            Schedule schedule = scheduleService.getScheduleById(memberDTO.getScheduleId())
                    .orElse(null); // handle case where schedule might not exist
            member.setSchedule(schedule);
        }

        // Check if the facultyId is not null and then set the new faculty
        if (memberDTO.getFacultyId() != null) {
            Faculty faculty = facultyService.getFacultyById(memberDTO.getFacultyId())
                    .orElse(null); // handle case where faculty might not exist
            member.setFaculty(faculty);
        }

        // Save the updated member
        Member updatedMember = memberService.saveMember(member);
        return ResponseEntity.ok(updatedMember);
    }
    @GetMapping("/profile")
    public ResponseEntity<MemberDTO> getProfile(Principal principal) {
        String email = principal.getName(); // Email is the username in this context
        Optional<Member> member = memberService.getMemberByEmail(email);
        return member.map(m -> {
            MemberDTO dto = new MemberDTO(m.getName(), m.getEmail(), m.getMembershipPackage());
            dto.setTrainerName(m.getTrainer() != null ? m.getTrainer().getName() : null);
            dto.setScheduleDetails(m.getSchedule() != null ? String.valueOf(m.getSchedule()) : null); // Make sure toString() provides meaningful information
            dto.setFacultyName(m.getFaculty() != null ? m.getFaculty().getName() : null);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}

