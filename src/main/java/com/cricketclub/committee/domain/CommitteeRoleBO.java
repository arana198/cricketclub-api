package com.cricketclub.committee.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(of={"id", "name", "visible"})
@ToString(of={"id", "name"})
@Entity
@Table(name = "committee_role")
public class CommitteeRoleBO {

    public enum CommitteeRole {
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

        CommitteeRole(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static CommitteeRole getFromString(String value) {
            for(CommitteeRole clubOfficer : CommitteeRole.values()) {
                if(clubOfficer.getValue().equals(value)) {
                    return clubOfficer;
                }
            }

            return null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private CommitteeRole name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_visible", nullable = false)
    private Boolean visible;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CommitteeRole getName() {
        return name;
    }

    public void setName(CommitteeRole name) {
        this.name = name;
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
