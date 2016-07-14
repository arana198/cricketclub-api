package com.cricketclub.team.domain;

import com.cricketclub.common.domain.AbstractAuditEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper=true, of={"teamsheet", "userId"})
@ToString(of={"id", "teamsheet", "userId"})
@Entity
@Table(name = "selected_player")
public class SelectedPlayerBO extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "teamsheet_id", referencedColumnName = "id", nullable = false)
    private TeamsheetBO teamsheet;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_available")
    private Boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TeamsheetBO getTeamsheet() {
        return teamsheet;
    }

    public void setTeamsheet(TeamsheetBO teamsheet) {
        this.teamsheet = teamsheet;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}