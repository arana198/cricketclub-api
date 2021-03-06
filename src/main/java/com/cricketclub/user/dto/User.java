package com.cricketclub.user.dto;


import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class User extends BaseDomain {

    private final Long userId;

    @Size(min = 1, max = 20, message = "username is wrong size")
    @NotBlank(message = "username is compulsory")
    @Pattern(regexp = "[A-Za-z0-9]*", message = "username has invalid characters")
    private final String username;

    @Size(min = 5, max = 50, message = "email is wrong size")
    @NotBlank(message = "email is compulsory")
    @Email(message = "email format incorrect")
    private final String email;
    private final RoleList roles;
    @Size(min = 6, max = 50, message = "password is wrong size")
    @NotBlank(message = "password is compulsory")
    private String password;

    @JsonCreator
    public User(@JsonProperty(value = "teamId") final Long userId,
                @JsonProperty(value = "username", required = true) final String username,
                @JsonProperty(value = "email", required = true) final String email,
                @JsonProperty(value = "roles", required = true) final RoleList roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
