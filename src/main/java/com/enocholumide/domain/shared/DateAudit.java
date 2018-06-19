package com.enocholumide.domain.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties( value = {"createdAt", "updatedAt"}, allowGetters = true )
public abstract class DateAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateAudit)) return false;
        DateAudit that = (DateAudit) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public Long getId(){
        return id;
    }
}