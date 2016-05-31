package com.cricketclub.domain.committee;

import com.cricketclub.domain.AbstractEntity;
import com.cricketclub.domain.user.UserBO;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true, of={"user", "committeeRole", "year"})
@ToString(of={"id", "user"})
@Entity
@Table(name = "elected_officers")
public class CommitteeMemberBO extends AbstractEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserBO user;

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

    public UserBO getUser() {
        return user;
    }

    public void setUser(UserBO user) {
        this.user = user;
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
