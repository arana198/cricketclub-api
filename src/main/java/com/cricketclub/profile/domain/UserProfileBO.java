package com.cricketclub.profile.domain;

import com.cricketclub.common.domain.AbstractAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = {"userId"})
@ToString(of = {"id"})
@Entity
@Table(name = "profile", indexes = {
        @Index(name = "ix_user_id", columnList = "user_id", unique = true)
})
public class UserProfileBO extends AbstractAuditEntity implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "mobile_phone", nullable = false)
    private String mobileNumber;

    @Column(name = "home_phone")
    private String homeNumber;
}
