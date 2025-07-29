package com.illsang.auth.service

import com.illsang.auth.domain.entity.AuthUserEntity
import com.illsang.auth.repository.AuthUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthUserService (
    private val authUserRepository: AuthUserRepository,
) {

    fun getUserByEmail(id: String): AuthUserEntity? {
        return authUserRepository.findByEmail(id)
    }

}
