package com.illsang.auth.service

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.illsang.auth.enums.OSType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class GoogleTokenValidationService {

    @Value("\${oauth.client.google.ios}")
    private lateinit var googleIosClientId: String

    @Value("\${oauth.client.google.aos}")
    private lateinit var googleAosClientId: String

    private val transport = NetHttpTransport()
    private val jsonFactory = GsonFactory.getDefaultInstance()

    fun validateIdToken(idToken: String, osType: OSType): GoogleIdToken.Payload {
        val clientId = when (osType) {
            OSType.IOS -> googleIosClientId
            OSType.AOS -> googleAosClientId
        }

        val verifier = GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            .setAudience(Collections.singletonList(clientId))
            .build()

        return try {
            val googleIdToken = verifier.verify(idToken)
                ?: throw IllegalArgumentException("Google ID token verification failed - token is invalid or expired")

            googleIdToken.payload
                ?: throw IllegalArgumentException("Google ID token payload is empty")
        } catch (e: IllegalArgumentException) {
            throw e
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to validate Google ID token: ${e.message}", e)
        }
    }
}
