package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.dto.request.user.MissionHistoryRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MissionHistoryCustomRepository {
    fun findSubmitUserMissionHistory(request: MissionHistoryRequest, pageable: Pageable): Page<UserMissionHistoryEntity>
}