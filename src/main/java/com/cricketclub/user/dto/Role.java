package com.cricketclub.user.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class Role extends BaseDomain {

    public enum UserRole {
        ROLE_ADMIN("ROLE_ADMIN"),
        ROLE_CLUB_ADMIN("ROLE_CLUB_ADMIN"),
        ROLE_CAPTAIN("ROLE_CAPTAIN"),
        ROLE_PLAYER("ROLE_PLAYER"),
        ROLE_USER("ROLE_USER");

        private String value;

        UserRole(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static UserRole getRoleFromString(String value) {
            for(UserRole role : UserRole.values()) {
                if(role.getValue().equalsIgnoreCase(value)) {
                    return role;
                }
            }

            return null;
        }
    }

    private final Integer roleId;

    @NotNull(message = "username is compulsory")
    private final UserRole name;

    @Size(min = 5, max = 50, message = "description is wrong size")
    @NotBlank(message = "description is compulsory")
    private final String description;

    @NotNull(message = "presedenceOrder is compulsory")
    private final Integer presedenceOrder;

    @JsonCreator
    public Role(@JsonProperty(value = "roleId") final Integer roleId,
                @JsonProperty(value = "name", required = true) final UserRole name,
                @JsonProperty(value = "description", required = true) final String description,
                @JsonProperty(value = "presedenceOrder", required = true) final Integer presedenceOrder) {
        this.roleId = roleId;
        this.name = name;
        this.description = description;
        this.presedenceOrder = presedenceOrder;
    }
}
