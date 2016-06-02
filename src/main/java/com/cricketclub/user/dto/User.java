package com.cricketclub.user.dto;


import com.cricketclub.common.dto.BaseDomain;
import com.cricketclub.committee.member.dto.CommitteeMemberList;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class User extends BaseDomain {

    private Long userId;

    @Size(min = 1, max = 20, message = "username is wrong size")
    @NotBlank(message = "username is compulsory")
    @Pattern(regexp = "[A-Za-z0-9]*", message = "username has invalid characters")
    private String username;

    @Size(min = 5, max = 50, message = "email is wrong size")
    @NotBlank(message = "email is compulsory")
    @Email(message = "email format incorrect")
    private String email;

    @Size(min = 6, max = 50, message = "password is wrong size")
    @NotBlank(message = "password is compulsory")
    private String password;

    @Size(min = 1, max = 50, message = "lastName is wrong size")
    @NotBlank(message = "lastName is compulsory")
    @Pattern(regexp = "[A-Za-z ]*", message = "lastName has invalid characters")
    private String firstName;

    @Size(min = 1, max = 50, message = "firstName is wrong size")
    @NotBlank(message = "firstName is compulsory")
    @Pattern(regexp = "[A-Za-z ]*", message = "firstName has invalid characters")
    private String lastName;

    private String homeNumber;

    @Size(min = 1, max = 14, message = "mobilePhone is wrong size")
    @NotBlank(message = "mobilePhone is compulsory")
    @Pattern(regexp = "^(07\\d{9})$", message = "mobilePhone has invalid characters")
    private String mobileNumber;

    private CommitteeMemberList committeeMemberList;
    private List<Role> roles = new ArrayList<Role>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public CommitteeMemberList getCommitteeMemberList() {
        return committeeMemberList;
    }

    public void setCommitteeMemberList(CommitteeMemberList committeeMemberList) {
        this.committeeMemberList = committeeMemberList;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
