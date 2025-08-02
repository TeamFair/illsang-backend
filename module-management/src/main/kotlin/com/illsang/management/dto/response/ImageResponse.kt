package com.illsang.management.dto.response

import com.illsang.management.domain.model.ImageModel
import com.illsang.management.enums.ImageType

data class ImageResponse(
    val id: String? = null,
    val type: ImageType,
    val fileName: String,
    val fileSize: Long,
) {

    companion object {
        fun from(image: ImageModel): ImageResponse {
            return ImageResponse(
                id = image.id,
                type = image.type,
                fileName = image.fileName,
                fileSize = image.fileSize,
            )
        }
    }

}
