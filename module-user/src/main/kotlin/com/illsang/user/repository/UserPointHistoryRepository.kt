package com.illsang.user.repository

import com.illsang.user.domain.entity.UserPointHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserPointHistoryRepository : JpaRepository<UserPointHistoryEntity, Long>
