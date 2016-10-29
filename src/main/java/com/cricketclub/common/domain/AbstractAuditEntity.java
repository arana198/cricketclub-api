package com.cricketclub.common.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode(of = {"createdTs", "updatedBy", "updatedTs"})
@ToString(of = {"createdTs", "updatedBy", "updatedTs"})
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class AbstractAuditEntity implements Serializable {

    private static final long serialVersionUID = 6384069660089559035L;

    @Transient
    private SecurityContext securityContext = SecurityContextHolder.getContext();

    @Column(name = "created_ts", nullable = false)
    private LocalDate createdTs;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @Column(name = "updated_ts", nullable = false)
    private LocalDate updatedTs;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @PrePersist
    protected void onCreate() {
        updatedTs = createdTs = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTs = LocalDate.now();
        if (securityContext.getAuthentication() != null) {
            String updateByUserId = securityContext.getAuthentication().getName();
            updatedBy = updateByUserId;
        }
    }

    public LocalDate getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(LocalDate createdTs) {
        this.createdTs = createdTs;
    }

    public LocalDate getUpdatedTs() {
        return updatedTs;
    }

    public void setUpdatedTs(LocalDate updatedTs) {
        this.updatedTs = updatedTs;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}