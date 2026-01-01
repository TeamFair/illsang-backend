package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.dto.request.quest.QuestGetListRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository : JpaRepository<QuestEntity, Long>, QuestUserCustomRepository, QuestCustomRepository{
    fun existsByMainImageId(imageId: String): Boolean
    fun existsByImageId(imageId: String): Boolean
}
