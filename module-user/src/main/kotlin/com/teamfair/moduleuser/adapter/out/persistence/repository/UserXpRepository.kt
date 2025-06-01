package com.teamfair.moduleuser.adapter.out.persistence.repository

import com.teamfair.moduleuser.adapter.out.persistence.entity.UserXpEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserXpRepository : JpaRepository<UserXpEntity, Long> {

} 