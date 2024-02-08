package com.example.demo.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.Instant;

@Data
@MappedSuperclass
public sealed class BaseEntity permits Customer {

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @CreatedBy
    @Column(name="created_by")
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Instant modifiedAt;

    @LastModifiedBy
    @Column(name="modified_by")
    private String modifiedBy;

}
