package com.illsang.management.service

import com.illsang.management.domain.entity.CommercialAreaEntity
import com.illsang.management.domain.model.MetroAreaModel
import com.illsang.management.repository.CommercialAreaRepository
import com.illsang.management.repository.MetroAreaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AreaService(
    private val metroAreaRepository: MetroAreaRepository,
    private val commercialAreaRepository: CommercialAreaRepository,
) {

    fun findAllMetro(): List<MetroAreaModel> {
        val metroAreas = this.metroAreaRepository.findAll()

        return metroAreas.map(MetroAreaModel::from)
    }

    fun existOrThrowCommercialArea(commercialAreaCode: String) =
        this.findCommercialAreaById(commercialAreaCode)

    private fun findCommercialAreaById(id: String): CommercialAreaEntity =
        this.commercialAreaRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Commercial Area Code not found with id: $id")

}
