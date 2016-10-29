package com.cricketclub.committee.domain;

import com.cricketclub.common.domain.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true, of = {"userId", "committeeRole", "year"})
@ToString(of = {"id", "userId"})
@Entity
@Table(name = "elected_officers")
public class CommitteeMemberBO extends AbstractEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToOne
    @JoinColumn(name = "committee_role_id", referencedColumnName = "id", nullable = false)
    private CommitteeRoleBO committeeRole;

    @Column(name = "year", nullable = false)
    private Integer year;
}
