package com.illsang.user.domain.entity

import com.illsang.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "user_point")
class UserPointEntity(
    @EmbeddedId
    val id: UserPointKey,

    @Column(name = "point")
    var point: Int = 0,
) : BaseEntity() {

    fun addPoint(point: Int) {
        this.point += point
    }

}
