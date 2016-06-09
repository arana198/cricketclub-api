package com.cricketclub.user.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(of={"id"})
@ToString(of={"id"})
@Entity
@Table(name = "user_password_token")
public class UserPasswordTokenBO implements Serializable {

    private static final long serialVersionUID = 2780670648745098454L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserBO user;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "created_ts", nullable = false)
    private Date createdTs;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Date createdTs) {
        this.createdTs = createdTs;
    }
}
