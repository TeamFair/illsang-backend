package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.repository.MissionRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.QuizRepository
import com.teamfair.modulequest.application.port.out.QuizPersistencePort
import com.teamfair.modulequest.domain.mapper.QuizMapper
import com.teamfair.modulequest.domain.model.QuizModel
import org.springframework.stereotype.Component

@Component
class QuizPersistenceAdapter(
    private val quizRepository: QuizRepository,
    private val missionRepository: MissionRepository
) : QuizPersistencePort {

    override fun save(quizModel: QuizModel): QuizModel {
        val missionEntity = missionRepository.findById(quizModel.missionId)
            .orElseThrow { IllegalArgumentException("Mission not found with id: ${quizModel.missionId}") }
        
        val entity = QuizMapper.toEntity(quizModel, missionEntity)
        val savedEntity = quizRepository.save(entity)
        return QuizMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): QuizModel? {
        return quizRepository.findById(id)
            .map { QuizMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<QuizModel> {
        return quizRepository.findAll()
            .map { QuizMapper.toModel(it) }
    }

    override fun findByMissionId(missionId: Long): List<QuizModel> {
        return quizRepository.findByMissionId(missionId)
            .map { QuizMapper.toModel(it) }
    }

    override fun deleteById(id: Long) {
        quizRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return quizRepository.existsById(id)
    }
} 