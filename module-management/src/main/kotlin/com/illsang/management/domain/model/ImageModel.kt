package com.illsang.management.domain.model

import com.illsang.common.domain.model.BaseModel
import com.illsang.management.domain.entity.ImageEntity
import com.illsang.management.enums.ImageType
import java.time.LocalDateTime

data class ImageModel(
    val id: String? = null,
    val type: ImageType,
    val fileName: String,
    val fileSize: Long,
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {

    companion object {
        fun from(image: ImageEntity): ImageModel {
            return ImageModel(
                id = image.id,
                type = image.type,
                fileName = image.fileName,
                fileSize = image.fileSize,
                createdAt = image.createdAt,
                createdBy = image.createdBy,
                updatedAt = image.updatedAt,
                updatedBy = image.updatedBy,
            )
        }
    }

}
