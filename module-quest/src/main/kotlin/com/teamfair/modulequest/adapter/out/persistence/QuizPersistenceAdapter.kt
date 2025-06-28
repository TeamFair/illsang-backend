package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.entity.MissionEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.QuizEntity
import com.teamfair.modulequest.adapter.out.persistence.repository.MissionRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.QuizRepository
import com.teamfair.modulequest.application.port.out.QuizPersistencePort
import com.teamfair.modulequest.domain.mapper.QuizMapper
import com.teamfair.modulequest.domain.model.Quiz
import org.springframework.stereotype.Component

@Component
class QuizPersistenceAdapter(
    private val quizRepository: QuizRepository,
    private val missionRepository: MissionRepository
) : QuizPersistencePort {

    override fun save(quiz: Quiz): Quiz {
        val missionEntity = missionRepository.findById(quiz.missionId)
            .orElseThrow { IllegalArgumentException("Mission not found with id: ${quiz.missionId}") }
        
        val entity = QuizMapper.toEntity(quiz, missionEntity)
        val savedEntity = quizRepository.save(entity)
        return QuizMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): Quiz? {
        return quizRepository.findById(id)
            .map { QuizMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<Quiz> {
        return quizRepository.findAll()
            .map { QuizMapper.toModel(it) }
    }

    override fun findByMissionId(missionId: Long): List<Quiz> {
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