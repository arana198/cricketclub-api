package com.cricketclub.api.resource.user;


import com.cricketclub.api.resource.BaseDomain;
import com.cricketclub.api.resource.committee.CommitteeMemberList;
import com.cricketclub.domain.user.RoleBO;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Role extends BaseDomain {

    private Integer roleId;

    @NotNull(message = "username is compulsory")
    private RoleBO.Role name;

    @Size(min = 5, max = 50, message = "description is wrong size")
    @NotBlank(message = "description is compulsory")
    private String description;

    @NotNull(message = "presedenceOrder is compulsory")
    private Integer presedenceOrder;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public RoleBO.Role getName() {
        return name;
    }

    public void setName(RoleBO.Role name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPresedenceOrder() {
        return presedenceOrder;
    }

    public void setPresedenceOrder(Integer presedenceOrder) {
        this.presedenceOrder = presedenceOrder;
    }
}
