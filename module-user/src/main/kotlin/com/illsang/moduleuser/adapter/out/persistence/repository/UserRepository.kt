package com.illsang.moduleuser.adapter.out.persistence.repository

import com.illsang.moduleuser.adapter.out.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}
