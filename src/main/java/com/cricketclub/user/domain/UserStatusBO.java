package com.cricketclub.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"id", "name", "selectable"})
@ToString(of = {"id", "name"})
@Entity
@Table(name = "user_status")
public class UserStatusBO implements Serializable {

    private static final long serialVersionUID = 1815696712482274042L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private UserStatus name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "is_selectable", nullable = false)
    private boolean selectable;

    public enum UserStatus {
        ACTIVE, PENDING, SUSPENDED, BLACKLISTED;
    }
}
