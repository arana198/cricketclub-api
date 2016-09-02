package com.cricketclub.user.domain;

import com.cricketclub.common.domain.AbstractAuditEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of={"id", "username"})
@ToString(of={"id", "username"})
@Entity
@Table(name = "user")
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
}
