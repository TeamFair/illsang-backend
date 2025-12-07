package com.illsang.management.service.storage

import org.springframework.web.multipart.MultipartFile

interface StorageService {
    fun uploadImage(fileName: String, file: MultipartFile)
    fun downloadImage(fileName: String): ByteArray
    fun deleteImage(fileName: String)
    fun exist(fileName: String): Boolean
}

