package com.cricketclub.user.dto;


import com.cricketclub.common.dto.BaseDomain;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User extends BaseDomain {

    private Long userId;

    @Size(min = 1, max = 20, message = "username is wrong size")
    @NotBlank(message = "username is compulsory")
    @Pattern(regexp = "[A-Za-z0-9]*", message = "username has invalid characters")
    private final String username;

    @Size(min = 5, max = 50, message = "email is wrong size")
    @NotBlank(message = "email is compulsory")
    @Email(message = "email format incorrect")
    private final String email;

    @Size(min = 6, max = 50, message = "password is wrong size")
    @NotBlank(message = "password is compulsory")
    private final String password;

    @Size(min = 1, max = 50, message = "lastName is wrong size")
    @NotBlank(message = "lastName is compulsory")
    @Pattern(regexp = "[A-Za-z ]*", message = "lastName has invalid characters")
    private final String firstName;

    @Size(min = 1, max = 50, message = "firstName is wrong size")
    @NotBlank(message = "firstName is compulsory")
    @Pattern(regexp = "[A-Za-z ]*", message = "firstName has invalid characters")
    private final String lastName;

    private final String homeNumber;

    @Size(min = 1, max = 14, message = "mobilePhone is wrong size")
    @NotBlank(message = "mobilePhone is compulsory")
    @Pattern(regexp = "^(07\\d{9})$", message = "mobilePhone has invalid characters")
    private final String mobileNumber;

    private final RoleList roles;

    public User(Long userId, String username, String email, String password, String firstName, String lastName, String homeNumber, String mobileNumber, RoleList roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeNumber = homeNumber;
        this.mobileNumber = mobileNumber;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public RoleList getRoles() {
        return roles;
    }
}
