package com.teamfair.illsang.core.common.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    var createdBy: String = "temp",

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedBy
    @Column(name = "updated_by", updatable = false)
    var updatedBy: String = "temp",

    @LastModifiedDate
    @Column(name = "updated_at", updatable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)
