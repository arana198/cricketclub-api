package com.cricketclub.user.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class RoleList extends BaseDomain {
    private final List<Role> roles;

    @JsonCreator
    public RoleList(@JsonProperty(value = "roles", required = true) List<Role> roles) {
        this.roles = roles;
    }
}
