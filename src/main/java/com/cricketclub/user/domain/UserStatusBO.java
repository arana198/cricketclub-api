package com.cricketclub.user.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(of={"id", "name", "selectable"})
@ToString(of={"id", "name"})
@Entity
@Table(name = "user_status")
public class UserStatusBO implements Serializable{

    private static final long serialVersionUID = 1815696712482274042L;

    public enum UserStatus{
        ACTIVE, PENDING, SUSPENDED, BLACKLISTED;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserStatus getName() {
        return name;
    }

    public void setName(UserStatus name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }
}
