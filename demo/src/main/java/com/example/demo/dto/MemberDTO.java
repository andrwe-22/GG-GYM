package com.example.demo.dto;

import com.example.demo.model.Faculty;
import com.example.demo.model.Schedule;
import com.example.demo.model.Trainer;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class MemberDTO {


    private Long id;

    public MemberDTO(Long id, String name, String email, String membershipPackage, Long trainerId, Long scheduleId, Long facultyId, String trainerName, String scheduleDetails, String facultyName, Schedule schedule) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.membershipPackage = membershipPackage;
        this.trainerId = trainerId;
        this.scheduleId = scheduleId;
        this.facultyId = facultyId;
        TrainerName = trainerName;
        ScheduleDetails = scheduleDetails;
        FacultyName = facultyName;
        this.schedule = schedule;
    }
    public MemberDTO(Long id, String name, String email, String membershipPackage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.membershipPackage = membershipPackage;
    }

    public MemberDTO() {
    }

    private String name;
    private String email;
    private String membershipPackage;

    private Long trainerId;
    private Long scheduleId;
    private Long facultyId;

    private String TrainerName;

    private String ScheduleDetails;

    private String FacultyName;

    public String getTrainerName() {
        return TrainerName;
    }

    public void setTrainerName(String trainerName) {
        TrainerName = trainerName;
    }

    public String getScheduleDetails() {
        return ScheduleDetails;
    }

    public void setScheduleDetails(String scheduleDetails) {
        ScheduleDetails = scheduleDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacultyName() {
        return FacultyName;
    }

    public void setFacultyName(String facultyName) {
        FacultyName = facultyName;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    private Schedule schedule;

    public MemberDTO(String name, String email, String membershipPackage) {
        this.name = name;
        this.email = email;
        this.membershipPackage = membershipPackage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMembershipPackage() {
        return membershipPackage;
    }

    public void setMembershipPackage(String membershipPackage) {
        this.membershipPackage = membershipPackage;
    }
}