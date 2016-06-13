package com.cricketclub.committee.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.cricketclub.committee.domain.CommitteeRoleBO;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CommitteeRole extends BaseDomain {

    private final Integer committeeRoleId;

    @NotNull(message = "committeeRole is compulsory")
    private final CommitteeRoleBO.CommitteeRole committeeRole;

    @Size(min = 1, max = 15, message = "displayName is wrong size")
    @NotBlank(message = "displayName is compulsory")
    @Pattern(regexp = "[A-Za-z ]*", message = "displayName has invalid characters")
    private final String displayName;

    @Size(min = 1, max = 255, message = "description is wrong size")
    @NotBlank(message = "description is compulsory")
    @Pattern(regexp = "[A-Za-z ]*", message = "description has invalid characters")
    private final String description;

    private final Boolean visible;

    public CommitteeRole(Integer committeeRoleId, CommitteeRoleBO.CommitteeRole committeeRole, String displayName, String description, Boolean visible) {
        this.committeeRoleId = committeeRoleId;
        this.committeeRole = committeeRole;
        this.displayName = displayName;
        this.description = description;
        this.visible = visible;
    }

    public Integer getCommitteeRoleId() {
        return committeeRoleId;
    }

    public CommitteeRoleBO.CommitteeRole getCommitteeRole() {
        return committeeRole;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getVisible() {
        return visible;
    }
}
