package com.teamfair.moduleuser.adapter.out.persistence.repository

import com.teamfair.moduleuser.adapter.out.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
} 