package com.illsang.auth.service

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.illsang.auth.enums.OSType
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.HttpClientErrorException
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

    fun getGoogleImplicitTokenInfo(accessToken: String): Map<String, String> {
        val restTemplate = RestTemplate()
        val tokenInfoUrl = "https://oauth2.googleapis.com/tokeninfo?access_token=$accessToken"

        return try {
            val response = restTemplate.getForObject(tokenInfoUrl, Map::class.java)
                ?: throw IllegalArgumentException("Google tokeninfo API returned null response")

            @Suppress("UNCHECKED_CAST")
            response as Map<String, String>
        } catch (e: HttpClientErrorException) {
            throw BadCredentialsException("Token is not valid")
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to validate Google access token: ${e.message}", e)
        }
    }
}
