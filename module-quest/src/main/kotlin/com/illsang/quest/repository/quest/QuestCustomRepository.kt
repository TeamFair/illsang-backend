package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.dto.request.quest.QuestGetListRequest
import com.illsang.quest.dto.request.quest.QuestUserRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QuestCustomRepository {
    fun findByRequest(request: QuestGetListRequest, pageable: Pageable): Page<QuestEntity>
}
