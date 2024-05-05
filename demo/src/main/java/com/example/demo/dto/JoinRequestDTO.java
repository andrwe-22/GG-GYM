package com.example.demo.dto;

public class JoinRequestDTO {
    private String name;
    private String email;

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

    private String membershipPackage;

    // Getters and setters
}
