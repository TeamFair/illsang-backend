package com.illsang.user.repository

import com.illsang.user.domain.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserPointCustomRepository {
    fun findAllTotalRank(commercialAreaCode: String, pageable: Pageable): Page<Pair<UserEntity, Long>>
}
