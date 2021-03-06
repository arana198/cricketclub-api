package com.cricketclub.team.domain;

import com.cricketclub.common.domain.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(of = {"team", "opposition", "fixtureDate", "fixtureType", "venue", "startTime"})
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
}
