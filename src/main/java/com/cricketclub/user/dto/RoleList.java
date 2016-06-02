package com.cricketclub.user.dto;


import com.cricketclub.common.dto.BaseDomain;

import java.util.ArrayList;
import java.util.List;

public class RoleList extends BaseDomain {

    private List<Role> roles = new ArrayList<Role>();

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
