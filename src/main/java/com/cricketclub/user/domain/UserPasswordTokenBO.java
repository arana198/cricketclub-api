package com.cricketclub.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = {"user"})
@ToString(of = {"id"})
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
    private LocalDateTime createdTs;
}
