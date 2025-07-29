package com.illsang.modulequest.application.service

import com.illsang.modulequest.application.command.CreateQuestCommand
import com.illsang.modulequest.application.command.UpdateQuestCommand
import com.illsang.modulequest.application.port.out.QuestPersistencePort
import com.illsang.modulequest.domain.mapper.QuestMapper
import com.illsang.modulequest.domain.model.QuestModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuestService(
    private val questPersistencePort: QuestPersistencePort
) {

    @Transactional
    fun createQuest(command: CreateQuestCommand): QuestModel {
        val questModel = QuestMapper.toModel(command)
        return questPersistencePort.save(questModel)
    }

    fun getQuestById(id: Long): QuestModel? {
        return questPersistencePort.findById(id)
    }

    fun getAllQuests(): List<QuestModel> {
        return questPersistencePort.findAll()
    }

    @Transactional
    fun updateQuest(command: UpdateQuestCommand): QuestModel? {
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
