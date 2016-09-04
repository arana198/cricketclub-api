package com.cricketclub.user.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.cricketclub.user.domain.RoleBO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class Role extends BaseDomain {
    private final Integer roleId;

    @NotNull(message = "username is compulsory")
    private final RoleBO.Role name;

    @Size(min = 5, max = 50, message = "description is wrong size")
    @NotBlank(message = "description is compulsory")
    private final String description;

    @NotNull(message = "presedenceOrder is compulsory")
    private final Integer presedenceOrder;

    @JsonCreator
    public Role(@JsonProperty(value = "roleId") final Integer roleId,
                @JsonProperty(value = "name", required = true) final RoleBO.Role name,
                @JsonProperty(value = "description", required = true) final String description,
                @JsonProperty(value = "presedenceOrder", required = true) final Integer presedenceOrder) {
        this.roleId = roleId;
        this.name = name;
        this.description = description;
        this.presedenceOrder = presedenceOrder;
    }
}
