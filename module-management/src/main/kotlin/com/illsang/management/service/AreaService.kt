package com.illsang.management.service

import com.illsang.management.domain.entity.CommercialAreaEntity
import com.illsang.management.domain.model.ImageModel
import com.illsang.management.dto.request.ImageCreateRequest
import com.illsang.management.domain.entity.ImageEntity
import com.illsang.management.domain.model.MetroAreaModel
import com.illsang.management.enums.ImageType
import com.illsang.management.repository.CommercialAreaRepository
import com.illsang.management.repository.ImageRepository
import com.illsang.management.repository.MetroAreaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

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

    fun existOrThrowCommercialArea(commercialAreaCode: String) {
        this.findCommercialAreaById(commercialAreaCode)
    }

    private fun findCommercialAreaById(id: String): CommercialAreaEntity =
        this.commercialAreaRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Commercial Area Code not found with id: $id")

}
