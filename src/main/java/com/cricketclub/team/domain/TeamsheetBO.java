package com.cricketclub.team.domain;

import com.cricketclub.common.domain.AbstractAuditEntity;
import com.cricketclub.common.domain.AbstractEntity;
import com.cricketclub.user.domain.RoleBO;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper=true, of={"fixture"})
@ToString(of={"id", "fixture"})
@Entity
@Table(name = "teamsheet")
public class TeamsheetBO extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fixture_id", referencedColumnName = "id", nullable = false)
    private FixtureBO fixture;

    @Column(name = "umpire")
    private String umpire;

    @Column(name = "scorer")
    private String scorer;

    @Column(name = "meet_time")
    private String meetTime;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="teamsheet")
    private List<SelectedPlayerBO> selectedPlayers = new ArrayList<SelectedPlayerBO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FixtureBO getFixture() {
        return fixture;
    }

    public void setFixture(FixtureBO fixture) {
        this.fixture = fixture;
    }

    public String getUmpire() {
        return umpire;
    }

    public void setUmpire(String umpire) {
        this.umpire = umpire;
    }

    public String getScorer() {
        return scorer;
    }

    public void setScorer(String scorer) {
        this.scorer = scorer;
    }

    public String getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(String meetTime) {
        this.meetTime = meetTime;
    }
}
