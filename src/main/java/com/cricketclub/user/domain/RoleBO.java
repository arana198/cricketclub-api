package com.cricketclub.user.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(of={"id"})
@ToString(of={"id", "name"})
@Entity
@Table(name = "role")
public class RoleBO implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -2934253177419534374L;

    public enum Role{
        ROLE_ADMIN("ROLE_ADMIN"),
        ROLE_CLUB_ADMIN("ROLE_CLUB_ADMIN"),
        ROLE_CAPTAIN("ROLE_CAPTAIN"),
        ROLE_PLAYER("ROLE_PLAYER"),
        ROLE_USER("ROLE_USER");

        private String value;

        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static Role getRoleFromString(String value) {
            for(Role role : Role.values()) {
                if(role.getValue().equalsIgnoreCase(value)) {
                    return role;
                }
            }

            return null;
        }
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private Role name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "presedence_order", nullable = false)
    private Integer presedenceOrder;

    @Column(name = "is_selectable", nullable = false)
    private boolean selectable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getName() {
        return name;
    }

    public void setName(Role name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPresedenceOrder() {
        return presedenceOrder;
    }

    public void setPresedenceOrder(Integer presedenceOrder) {
        this.presedenceOrder = presedenceOrder;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        selectable = selectable;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }

}
