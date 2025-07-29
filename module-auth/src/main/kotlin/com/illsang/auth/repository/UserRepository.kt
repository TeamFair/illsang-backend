package com.illsang.auth.repository

import com.illsang.auth.domain.entity.AuthUserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuthUserRepository : JpaRepository<AuthUserEntity, String> {
    fun findByEmail(email: String): AuthUserEntity?
}
