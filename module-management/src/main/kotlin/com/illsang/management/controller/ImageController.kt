package com.illsang.management.controller

import com.illsang.common.enums.ResponseMsg
import com.illsang.management.dto.request.ImageCreateRequest
import com.illsang.management.dto.response.ImageResponse
import com.illsang.management.enums.ImageType
import com.illsang.management.service.ImageService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.*
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/image")
@Tag(name = "Image", description = "이미지")
class ImageController(
    private val imageService: ImageService,
) {

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(operationId = "IMG001", summary= "이미지 생성")
    fun createImage(
        @RequestBody request: ImageCreateRequest,
    ): ResponseEntity<ImageResponse> {
        val image = this.imageService.createImage(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(ImageResponse.from(image))
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(operationId = "IMG002", summary= "이미지 다운로드")
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

    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(operationId = "IMG003", summary= "이미지 유형별 검색")
    fun getImagesByType(
        @PathVariable type: ImageType,
    ): ResponseEntity<List<ImageResponse>> {
        val images = this.imageService.getImagesByType(type)

        return ResponseEntity.ok(images.map { ImageResponse.from(it) })
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(operationId = "IMG004", summary= "이미지 삭제")
    fun deleteImage(
        @PathVariable id: String,
    ): ResponseEntity<ResponseMsg> {
        this.imageService.deleteImage(id)

        return ResponseEntity.ok(ResponseMsg.SUCCESS)
    }

}
