package com.cricketclub.team.domain;

import com.cricketclub.common.domain.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(of = {"fixture"})
@ToString(of = {"id", "fixture"})
@Entity
@Table(name = "teamsheet")
public class TeamsheetBO extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teamsheet")
    private List<SelectedPlayerBO> selectedPlayers = new ArrayList<SelectedPlayerBO>();
}
