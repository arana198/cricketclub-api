package com.cricketclub.committee.member.domain;

import com.cricketclub.committee.role.domain.CommitteeRoleBO;
import com.cricketclub.common.domain.AbstractEntity;
import com.cricketclub.user.domain.UserBO;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true, of={"userId", "committeeRole", "year"})
@ToString(of={"id", "userId"})
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

    public CommitteeRoleBO getCommitteeRole() {
        return committeeRole;
    }

    public void setCommitteeRole(CommitteeRoleBO committeeRole) {
        this.committeeRole = committeeRole;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
