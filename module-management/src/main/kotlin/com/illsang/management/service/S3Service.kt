package com.illsang.management.service

import com.illsang.management.service.storage.StorageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.ResponseBytes
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.core.sync.RequestBody.fromBytes
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.NoSuchKeyException
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.IOException

@Service
@Profile("!local")
class S3StorageService(
    private val s3Client: S3Client
) : StorageService {

    @Value("\${cloud.bucket.name}")
    private val bucketName: String? = null

    override fun uploadImage(fileName: String, file: MultipartFile) {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .contentType(file.contentType)
            .build()

        s3Client.putObject(putObjectRequest, fromBytes(file.bytes))
    }

    override fun downloadImage(fileName: String): ByteArray {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .build()

        val responseBytes: ResponseBytes<*> = s3Client.getObjectAsBytes(getObjectRequest)
        return responseBytes.asByteArray()
    }

    override fun deleteImage(fileName: String) {
        val deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .build()

        s3Client.deleteObject(deleteObjectRequest)
    }

    override fun exist(fileName: String): Boolean {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .build()

        return try {
            s3Client.getObject(getObjectRequest)
            true
        } catch (e: NoSuchKeyException) {
            false
        } catch (e: SdkException) {
            e.printStackTrace()
            throw IOException("Error checking existence of file in S3: $fileName")
        }
    }
}
