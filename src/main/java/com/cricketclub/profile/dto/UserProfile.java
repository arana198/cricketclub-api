package com.cricketclub.profile.dto;


import com.cricketclub.common.dto.BaseDomain;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Data
public class UserProfile extends BaseDomain {

    @Size(min = 5, max = 50, message = "email is wrong size")
    @NotBlank(message = "email is compulsory")
    @Email(message = "email format incorrect")
    private final String email;
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
    private final LocalDate dateOfBirth;
    @URL
    private final String imageUrl;
    private final String description;
    private Long userId;
}
