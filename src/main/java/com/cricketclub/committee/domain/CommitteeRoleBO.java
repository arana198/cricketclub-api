package com.cricketclub.committee.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = {"id", "name", "visible"})
@ToString(of = {"id", "name"})
@Entity
@Table(name = "committee_role")
public class CommitteeRoleBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "visible", nullable = false)
    private Boolean visible;
}
