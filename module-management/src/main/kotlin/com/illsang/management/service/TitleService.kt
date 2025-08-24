package com.illsang.management.service

import com.illsang.management.domain.entity.TitleEntity
import com.illsang.management.domain.model.TitleModel
import com.illsang.management.dto.request.TitleRequest
import com.illsang.management.repository.TitleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TitleService(
    private val titleRepository: TitleRepository,
) {

    fun getTitle(id: String): TitleModel {
        val title = this.findById(id)
        return TitleModel.from(title)
    }

    fun getAllTitles() : List<TitleModel> {
        return titleRepository.findAll()
            .map { TitleModel.from(it) }
    }

    @Transactional
    fun createTitle(request: TitleRequest): TitleModel {
        val title = request.toEntity()
        return TitleModel.from(titleRepository.save(title))
    }

    @Transactional
    fun updateTitle(id: String, request: TitleRequest): TitleModel {
        val title = this.findById(id)
        title.update(request)
        return TitleModel.from(title)
    }

    @Transactional
    fun deleteTitle(id: String) {
        titleRepository.deleteById(id)
    }

    private fun findById(id: String): TitleEntity =
        titleRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Title not found with id: $id")
}