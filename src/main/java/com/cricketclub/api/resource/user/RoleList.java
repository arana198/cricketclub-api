package com.cricketclub.api.resource.user;


import com.cricketclub.api.resource.BaseDomain;
import com.cricketclub.api.resource.committee.CommitteeMember;
import com.cricketclub.domain.user.RoleBO;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
