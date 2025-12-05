package com.illsang.management.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.common.enums.ReportStatusType
import com.illsang.common.enums.ReportType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "report")
class ReportEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "target_id")
    val targetId: Long,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    val type: ReportType,

    @Column(name = "user_id")
    val userId: String,

    @Column(name = "reason")
    val reason: String? = null,

    ): BaseEntity() {

}