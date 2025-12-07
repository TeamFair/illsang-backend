package com.illsang.management.service

import com.illsang.management.service.storage.StorageService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.ConcurrentHashMap

@Service
@Profile("local")
class InMemoryStorageService : StorageService {
    private val store = ConcurrentHashMap<String, ByteArray>()

    override fun uploadImage(fileName: String, file: MultipartFile) {
        store[fileName] = file.bytes
    }

    override fun downloadImage(fileName: String): ByteArray {
        return store[fileName] ?: throw IllegalArgumentException("Image not found: $fileName")
    }

    override fun deleteImage(fileName: String) {
        store.remove(fileName)
    }

    override fun exist(fileName: String): Boolean {
        return store.containsKey(fileName)
    }
}

