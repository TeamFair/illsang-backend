package com.illsang.management.service

import com.illsang.management.domain.entity.MetroAreaEntity
import com.illsang.management.domain.model.MetroAreaModel
import com.illsang.management.dto.request.MetroCreateRequest
import com.illsang.management.dto.request.MetroUpdateRequest
import com.illsang.management.repository.MetroAreaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AreaMetroService(
    private val metroAreaRepository: MetroAreaRepository,
) {

    fun findAllMetro(): List<MetroAreaModel> {
        val metroAreas = this.metroAreaRepository.findAll()

        return metroAreas.map(MetroAreaModel::from)
    }

    fun findByMetroCodes(metroAreaCodes: List<String>): List<MetroAreaModel> {
        val metros = this.metroAreaRepository.findAllById(metroAreaCodes)

        return metros.map(MetroAreaModel::from)
    }

    fun findMetroBy(metroAreaCode: String): MetroAreaModel {
        val metro = this.findMetroAreaById(metroAreaCode)

        return MetroAreaModel.from(metro)
    }

    @Transactional
    fun createMetro(request: MetroCreateRequest): MetroAreaModel {
        this.metroAreaRepository.findByIdOrNull(request.code)?.let {
            throw IllegalArgumentException("Metro Area Code already exists with id: ${request.code}")
        }

        val metro = request.toEntity()
        this.metroAreaRepository.save(metro)

        return MetroAreaModel.from(metro)
    }

    @Transactional
    fun updateMetro(metroAreaCode: String, request: MetroUpdateRequest): MetroAreaModel {
        if (metroAreaCode != request.code) {
            this.metroAreaRepository.findByIdOrNull(request.code)?.let {
                throw IllegalArgumentException("Metro Area Code already exists with id: ${request.code}")
            }
        }

        val metro = this.findMetroAreaById(metroAreaCode)
        metro.update(request)

        return MetroAreaModel.from(metro)
    }

    @Transactional
    fun deleteMetro(metroAreaCode: String) {
        val metro = this.findMetroAreaById(metroAreaCode)

        this.metroAreaRepository.delete(metro)
    }

    fun findMetroAreaById(id: String): MetroAreaEntity =
        this.metroAreaRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Metro Area Code not found with id: $id")

}
