package com.teamfair.modulequest.application.service

import com.teamfair.modulequest.application.command.CreateQuestCommand
import com.teamfair.modulequest.application.command.UpdateQuestCommand
import com.teamfair.modulequest.application.port.out.QuestPersistencePort
import com.teamfair.modulequest.domain.mapper.QuestMapper
import com.teamfair.modulequest.domain.model.Quest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuestService(
    private val questPersistencePort: QuestPersistencePort
) {

    @Transactional
    fun createQuest(command: CreateQuestCommand): Quest {
        val questModel = QuestMapper.toModel(command)
        return questPersistencePort.save(questModel)
    }

    fun getQuestById(id: Long): Quest? {
        return questPersistencePort.findById(id)
    }

    fun getAllQuests(): List<Quest> {
        return questPersistencePort.findAll()
    }

    @Transactional
    fun updateQuest(command: UpdateQuestCommand): Quest? {
        val existingQuest = questPersistencePort.findById(command.id) ?: return null
        val updatedQuest = QuestMapper.toModel(command, existingQuest)
        return questPersistencePort.save(updatedQuest)
    }

    @Transactional
    fun deleteQuest(id: Long): Boolean {
        return if (questPersistencePort.existsById(id)) {
            questPersistencePort.deleteById(id)
            true
        } else {
            false
        }
    }
} 