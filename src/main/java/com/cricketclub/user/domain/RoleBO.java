package com.cricketclub.user.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString(of={"id", "name"})
@Entity
@Table(name = "role")
public class RoleBO implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -2934253177419534374L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "presedence_order", nullable = false)
    private Integer presedenceOrder;

    @Column(name = "is_selectable", nullable = false)
    private boolean selectable;

    @Override
    public String getAuthority() {
        return name;
    }

}
