package com.cricketclub.team.domain;

import com.cricketclub.common.domain.AbstractEntity;
import com.cricketclub.user.domain.RoleBO;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true, of={"id"})
@ToString(of={"team", "opposition", "fixtureDate", "fixtureType", "venue", "startTime"})
@Entity
@Table(name = "fixture")
public class FixtureBO extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    private TeamBO team;

    @Column(name = "opposition", nullable = false)
    private String opposition;

    @Column(name = "fixture_date", nullable = false)
    private LocalDate fixtureDate;

    @Column(name = "fixture_type", nullable = false)
    private String fixtureType;

    @Column(name = "venue", nullable = false)
    private String venue;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamsheet_id", referencedColumnName = "id")
    private TeamsheetBO teamsheet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TeamsheetBO getTeamsheet() {
        return teamsheet;
    }

    public void setTeamsheet(TeamsheetBO teamsheet) {
        this.teamsheet = teamsheet;
    }

    public TeamBO getTeam() {
        return team;
    }

    public void setTeam(TeamBO team) {
        this.team = team;
    }

    public String getFixtureType() {
        return fixtureType;
    }

    public void setFixtureType(String fixtureType) {
        this.fixtureType = fixtureType;
    }

    public LocalDate getFixtureDate() {
        return fixtureDate;
    }

    public void setFixtureDate(LocalDate fixtureDate) {
        this.fixtureDate = fixtureDate;
    }

    public String getOpposition() {
        return opposition;
    }

    public void setOpposition(String opposition) {
        this.opposition = opposition;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
