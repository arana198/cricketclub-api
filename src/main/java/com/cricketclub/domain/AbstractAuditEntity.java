package com.cricketclub.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(of={"createdTs", "updatedBy", "updatedTs"})
@ToString(of={"createdTs", "updatedBy", "updatedTs"})
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class AbstractAuditEntity implements Serializable {

    private static final long serialVersionUID = 6384069660089559035L;

    @Transient
    private SecurityContext securityContext = SecurityContextHolder.getContext();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_ts", nullable = false)
    private Date createdTs;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_ts", nullable = false)
    private Date updatedTs;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @PrePersist
    protected void onCreate() {
        updatedTs = createdTs = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTs = new Date();
        if(securityContext.getAuthentication() != null){
            String updateByUserId = securityContext.getAuthentication().getName();
            updatedBy = updateByUserId;
        }
    }

    public Date getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Date createdTs) {
        this.createdTs = createdTs;
    }

    public Date getUpdatedTs() {
        return updatedTs;
    }

    public void setUpdatedTs(Date updatedTs) {
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