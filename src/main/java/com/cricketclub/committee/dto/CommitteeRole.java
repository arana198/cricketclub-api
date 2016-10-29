package com.cricketclub.committee.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CommitteeRole extends BaseDomain {

    public enum Role {
        PRESIDENT("PRESIDENT"),
        CHAIRMAN("CHAIRMAN"),
        SECRATORY("SECRATORY"),
        TREASURER("TREASURER"),
        MATCH_SECRATORY("MATCH_SECRATORY"),
        YOUTH_SECRATORY("YOUTH_SECRATORY"),
        WELFARE_OFFICER("WELFARE_OFFICER"),
        JUNIOR_DEVELOPMENT_OFFICER("JUNIOR_DEVELOPMENT_OFFICER"),
        PUBLICITY_OFFICER("PUBLICITY_OFFICER"),
        SOCIAL_OFFICER("SOCIAL_OFFICER");

        private String value;

        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Role getFromString(String value) {
            for(Role clubOfficer : Role.values()) {
                if(clubOfficer.getValue().equals(value)) {
                    return clubOfficer;
                }
            }

            return null;
        }
    }

    private final Integer committeeRoleId;

    @NotNull(message = "committeeRole is compulsory")
    private final Role committeeRole;

    @Size(min = 1, max = 15, message = "displayName is wrong size")
    @NotBlank(message = "displayName is compulsory")
    @Pattern(regexp = "[A-Za-z ]*", message = "displayName has invalid characters")
    private final String displayName;

    @Size(min = 1, max = 255, message = "description is wrong size")
    @NotBlank(message = "description is compulsory")
    @Pattern(regexp = "[A-Za-z ]*", message = "description has invalid characters")
    private final String description;

    private final Boolean visible;

    @JsonCreator
    public CommitteeRole(@JsonProperty(value = "committeeRoleId") final Integer committeeRoleId,
                         @JsonProperty(value = "committeeRole", required = true) final Role committeeRole,
                         @JsonProperty(value = "displayName", required = true) final String displayName,
                         @JsonProperty(value = "description", required = true) final String description,
                         @JsonProperty(value = "visible", required = true) final Boolean visible) {
        this.committeeRoleId = committeeRoleId;
        this.committeeRole = committeeRole;
        this.displayName = displayName;
        this.description = description;
        this.visible = visible;
    }
}
