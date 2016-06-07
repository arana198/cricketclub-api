package com.cricketclub.user.dto;


import com.cricketclub.common.dto.BaseDomain;

import java.util.ArrayList;
import java.util.List;

public class RoleList extends BaseDomain {

    private final List<Role> roles;

    public RoleList(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
