package com.cricketclub.user.domain;

import com.cricketclub.common.domain.AbstractAuditEntity;
import com.cricketclub.common.domain.profile.UserProfileBO;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, of={"id", "username"})
@ToString(of={"id", "username"})
@Entity
@Table(name = "service")
public class UserBO extends AbstractAuditEntity implements Serializable {

    private static final long serialVersionUID = -5889526109417397633L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @NotAudited
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleBO> roles = new HashSet<RoleBO>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_id", referencedColumnName = "id", nullable = false)
    private UserStatusBO userStatusBO;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "service")
    private UserProfileBO userProfile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleBO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleBO> roles) {
        this.roles = roles;
    }

    public UserStatusBO getUserStatusBO() {
        return userStatusBO;
    }

    public void setUserStatusBO(UserStatusBO userStatusBO) {
        this.userStatusBO = userStatusBO;
    }

    public UserProfileBO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileBO userProfile) {
        this.userProfile = userProfile;
    }
}