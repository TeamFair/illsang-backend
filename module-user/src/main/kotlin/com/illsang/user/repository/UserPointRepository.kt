package com.illsang.user.repository

import com.illsang.user.domain.entity.UserPointEntity
import com.illsang.user.domain.entity.UserPointKey
import org.springframework.data.jpa.repository.JpaRepository

interface UserPointRepository : JpaRepository<UserPointEntity, UserPointKey>, UserPointCustomRepository
