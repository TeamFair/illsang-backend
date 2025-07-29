package com.illsang.management.controller

import com.illsang.common.enums.ResponseMsg
import com.illsang.management.dto.request.ImageCreateRequest
import com.illsang.management.dto.response.ImageResponse
import com.illsang.management.enums.ImageType
import com.illsang.management.service.ImageService
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/image")
class ImageController(
    private val imageService: ImageService
) {

    @PostMapping
    fun createImage(
        @RequestBody request: ImageCreateRequest,
    ): ResponseEntity<ImageResponse> {
        val image = this.imageService.createImage(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(ImageResponse.from(image))
    }

    @GetMapping("/{id}")
    fun downloadImage(
        @PathVariable id: String,
    ): ResponseEntity<ByteArray> {
        val image = this.imageService.downloadImage(id)

        val contentType: MediaType = MediaType.IMAGE_JPEG

        val headers = HttpHeaders()
        headers.contentType = contentType
        headers.contentDisposition = ContentDisposition
            .builder("inline")
            .filename("$id.${contentType.subtype}")
            .build()

        return ResponseEntity.ok(image)
    }

    @GetMapping("/{type}")
    fun getImagesByType(
        @PathVariable type: ImageType,
    ): ResponseEntity<List<ImageResponse>> {
        val images = this.imageService.getImagesByType(type)

        return ResponseEntity.ok(images.map { ImageResponse.from(it) })
    }

    @DeleteMapping("/{id}")
    fun deleteImage(
        @PathVariable id: String,
    ): ResponseEntity<ResponseMsg> {
        this.imageService.deleteImage(id)

        return ResponseEntity.ok(ResponseMsg.SUCCESS)
    }

}
