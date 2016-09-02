package com.cricketclub.team.domain;

import com.cricketclub.common.domain.AbstractAuditEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
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
}