package com.teamfair.modulequest.adapter.out.persistence.repository

import com.teamfair.modulequest.adapter.out.persistence.entity.UserQuestHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserQuestHistoryRepository : JpaRepository<UserQuestHistoryEntity, Long> 