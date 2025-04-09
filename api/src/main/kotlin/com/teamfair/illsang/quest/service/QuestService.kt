package com.teamfair.illsang.quest.service

import com.teamfair.illsang.core.quest.repository.QuestRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuestService(
    val questRepository: QuestRepository,
) {
}
