package com.cricketclub.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(of={"createdTs", "updatedTs"})
@ToString(of={"createdTs", "updatedTs"})
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 6384069660089559035L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_ts", nullable = false)
    private Date createdTs;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_ts", nullable = false)
    private Date updatedTs;

    @PrePersist
    protected void onCreate() {
        updatedTs = createdTs = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTs = new Date();
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
}