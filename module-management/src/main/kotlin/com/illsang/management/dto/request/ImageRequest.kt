package com.illsang.management.dto.request

import com.illsang.management.domain.entity.ImageEntity
import com.illsang.management.enums.ImageType
import org.springframework.web.multipart.MultipartFile

data class ImageCreateRequest(
    val type : ImageType,
    val file: MultipartFile
) {

    fun toEntity(fileName: String): ImageEntity {
        return ImageEntity(
            id = fileName,
            type = type,
            fileSize = file.size,
            fileName = file.originalFilename!!
        )
    }

}
