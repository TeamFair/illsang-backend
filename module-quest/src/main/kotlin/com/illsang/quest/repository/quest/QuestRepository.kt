package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.QuestEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository : JpaRepository<QuestEntity, Long>, QuestUserCustomRepository{
    fun existsByMainImageId(imageId: String): Boolean
    fun existsByImageId(imageId: String): Boolean
}
