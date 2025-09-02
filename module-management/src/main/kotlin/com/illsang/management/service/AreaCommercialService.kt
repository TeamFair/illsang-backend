package com.illsang.management.service

import com.illsang.management.domain.entity.CommercialAreaEntity
import com.illsang.management.domain.model.CommercialAreaModel
import com.illsang.management.dto.request.CommercialCreateRequest
import com.illsang.management.dto.request.CommercialUpdateRequest
import com.illsang.management.repository.CommercialAreaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AreaCommercialService(
    private val metroService: AreaMetroService,
    private val commercialAreaRepository: CommercialAreaRepository,
) {

    fun existOrThrowCommercialArea(commercialAreaCode: String) =
        this.findCommercialAreaById(commercialAreaCode)

    fun findByCommercialCodes(commercialAreaCodes: List<String>): List<CommercialAreaModel> {
        val commercials = this.commercialAreaRepository.findAllById(commercialAreaCodes)

        return commercials.map(CommercialAreaModel::from)
    }

    fun findCommercialBy(commercialAreaCode: String): CommercialAreaModel {
        val commercial = this.findCommercialAreaById(commercialAreaCode)

        return CommercialAreaModel.from(commercial)
    }

    fun findAllCommercial(): List<CommercialAreaModel> {
        val commercials = this.commercialAreaRepository.findAll()
        return commercials.map(CommercialAreaModel::from)
    }

    @Transactional
    fun createCommercial(request: CommercialCreateRequest): CommercialAreaModel {
        val metro = this.metroService.findMetroAreaById(request.metroAreaCode)
        this.commercialAreaRepository.findByIdOrNull(request.code)?.let {
            throw IllegalArgumentException("Commercial Area Code already exists with id: ${request.code}")
        }

        val commercial = request.toEntity(metro)
        this.commercialAreaRepository.save(commercial)

        return CommercialAreaModel.from(commercial)
    }

    @Transactional
    fun updateCommercial(commercialAreaCode: String, request: CommercialUpdateRequest): CommercialAreaModel {
        if (commercialAreaCode != request.code) {
            this.commercialAreaRepository.findByIdOrNull(request.code)?.let {
                throw IllegalArgumentException("Commercial Area Code already exists with id: ${request.code}")
            }
        }

        val commercial = this.findCommercialAreaById(commercialAreaCode)
        commercial.update(request)

        return CommercialAreaModel.from(commercial)
    }

    @Transactional
    fun deleteCommercial(commercialAreaCode: String) {
        val commercial = this.findCommercialAreaById(commercialAreaCode)

        this.commercialAreaRepository.delete(commercial)
    }

    private fun findCommercialAreaById(id: String): CommercialAreaEntity =
        this.commercialAreaRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Commercial Area Code not found with id: $id")

}
