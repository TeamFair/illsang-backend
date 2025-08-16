package com.illsang.user.repository

import com.illsang.user.domain.entity.UserTitleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserTitleRepository : JpaRepository<UserTitleEntity, String>
