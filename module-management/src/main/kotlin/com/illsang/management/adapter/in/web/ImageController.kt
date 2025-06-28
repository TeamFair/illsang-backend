package com.illsang.management.adapter.`in`.web

import com.illsang.common.enums.ResponseMsg
import com.illsang.management.application.command.CreateImageCommand
import com.illsang.management.application.command.UpdateImageCommand
import com.illsang.management.application.service.ImageService
import com.illsang.management.domain.model.ImageModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/images")
class ImageController(
    private val imageService: ImageService
) {

    /**
     * create image
     */
    @PostMapping
    fun createImage(@RequestBody command: CreateImageCommand): ResponseEntity<ImageModel> {
        val createdImage = imageService.createImage(command)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdImage)
    }

    /**
     * get image
     */
    @GetMapping("/{id}")
    fun getImage(@PathVariable id: Long): ResponseEntity<ImageModel> {
        val image = imageService.getImage(id)
        return ResponseEntity.ok(image)
    }

    /**
     * get all images
     */
    @GetMapping
    fun getAllImages(): ResponseEntity<List<ImageModel>> {
        val images = imageService.getAllImages()
        return ResponseEntity.ok(images)
    }

    /**
     * get all images by type
     */
    @GetMapping("/type/{type}")
    fun getImagesByType(@PathVariable type: String): ResponseEntity<List<ImageModel>> {
        val images = imageService.getImagesByType(type)
        return ResponseEntity.ok(images)
    }

    /**
     * update image
     */
    @PutMapping("/{id}")
    fun updateImage(
        @PathVariable id: Long,
        @RequestBody command: UpdateImageCommand
    ): ResponseEntity<ImageModel> {
        val updatedCommand = command.copy(id = id)
        val updatedImage = imageService.updateImage(updatedCommand)
        return ResponseEntity.ok(updatedImage)
    }

    /**
     * delete image
     */
    @DeleteMapping("/{id}")
    fun deleteImage(@PathVariable id: Long): ResponseEntity<ResponseMsg> {
        imageService.deleteImage(id)
        return ResponseEntity.ok(ResponseMsg.SUCCESS)
    }
} 