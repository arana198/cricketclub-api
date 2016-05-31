package com.cricketclub.api.resource.committee;

import com.cricketclub.api.resource.BaseDomain;
import com.cricketclub.domain.committee.CommitteeRoleBO;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CommitteeRole extends BaseDomain {

    private Integer committeeRoleId;

    @NotNull(message = "committeeRole is compulsory")
    private CommitteeRoleBO.CommitteeRole committeeRole;

    @Size(min = 1, max = 15, message = "displayName is wrong size")
    @NotBlank(message = "displayName is compulsory")
    @Pattern(regexp = "[A-Za-z ]*", message = "displayName has invalid characters")
    private String displayName;

    @Size(min = 1, max = 255, message = "description is wrong size")
    @NotBlank(message = "description is compulsory")
    @Pattern(regexp = "[A-Za-z ]*", message = "description has invalid characters")
    private String description;

    private Boolean visible;

    public Integer getCommitteeRoleId() {
        return committeeRoleId;
    }

    public void setCommitteeRoleId(Integer committeeRoleId) {
        this.committeeRoleId = committeeRoleId;
    }

    public CommitteeRoleBO.CommitteeRole getCommitteeRole() {
        return committeeRole;
    }

    public void setCommitteeRole(CommitteeRoleBO.CommitteeRole committeeRole) {
        this.committeeRole = committeeRole;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
