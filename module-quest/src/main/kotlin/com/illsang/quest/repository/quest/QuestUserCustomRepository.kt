package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.dto.request.quest.QuestUserRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QuestUserCustomRepository {
    fun findAllUncompletedQuest(request: QuestUserRequest, pageable: Pageable): Page<QuestEntity>
}
