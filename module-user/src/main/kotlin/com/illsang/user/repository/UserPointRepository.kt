package com.illsang.user.repository

import com.illsang.user.domain.entity.UserPointEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserPointRepository : JpaRepository<UserPointEntity, Long>
