package com.illsang.moduleuser.application.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.illsang.moduleuser.domain.enums.OSType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.Signature
import java.security.spec.RSAPublicKeySpec
import java.util.*

@Service
class AppleTokenValidationService {

    @Value("\${oauth.client.apple.ios}")
    private lateinit var appleIosClientId: String

    @Value("\${oauth.client.apple.aos}")
    private lateinit var appleAosClientId: String

    private val restTemplate = RestTemplate()
    private val objectMapper = ObjectMapper()

    companion object {
        private const val APPLE_KEYS_URL = "https://appleid.apple.com/auth/keys"
        private const val APPLE_ISSUER = "https://appleid.apple.com"
        private const val JWT_PARTS_COUNT = 3
        private const val SIGNATURE_ALGORITHM = "SHA256withRSA"
        private const val RSA_ALGORITHM = "RSA"
        private const val MILLISECONDS_TO_SECONDS = 1000L
        private const val FIELD_KID = "kid"
        private const val FIELD_KEYS = "keys"
        private const val FIELD_AUD = "aud"
        private const val FIELD_ISS = "iss"
        private const val FIELD_EXP = "exp"
        private const val FIELD_N = "n"
        private const val FIELD_E = "e"
    }

    fun validateIdToken(idToken: String, osType: OSType): Map<String, Any> {
        val clientId = when (osType) {
            OSType.IOS -> appleIosClientId
            OSType.AOS -> appleAosClientId
        }

        return try {
            val parts = idToken.split(".")
            if (parts.size != JWT_PARTS_COUNT) {
                throw IllegalArgumentException("Apple ID token must have exactly 3 parts (header.payload.signature)")
            }

            val header = decodeBase64Url(parts[0])
            val payload = decodeBase64Url(parts[1])
            val signature = parts[2]

            val headerMap = objectMapper.readValue(header, Map::class.java) as Map<String, Any>
            val payloadMap = objectMapper.readValue(payload, Map::class.java) as Map<String, Any>

            // Validate signature
            if (!verifySignature(parts[0], parts[1], signature, headerMap[FIELD_KID] as String)) {
                throw IllegalArgumentException("Apple ID token signature verification failed")
            }

            // Validate audience (client ID)
            if (payloadMap[FIELD_AUD] != clientId) {
                throw IllegalArgumentException("Apple ID token audience mismatch - expected: $clientId, actual: ${payloadMap[FIELD_AUD]}")
            }

            // Validate issuer
            if (payloadMap[FIELD_ISS] != APPLE_ISSUER) {
                throw IllegalArgumentException("Apple ID token issuer mismatch - expected: $APPLE_ISSUER, actual: ${payloadMap[FIELD_ISS]}")
            }

            // Validate expiration
            val exp = (payloadMap[FIELD_EXP] as Number).toLong()
            if (exp < System.currentTimeMillis() / MILLISECONDS_TO_SECONDS) {
                throw IllegalArgumentException("Apple ID token has expired")
            }

            payloadMap
        } catch (e: IllegalArgumentException) {
            throw e
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to validate Apple ID token: ${e.message}", e)
        }
    }

    private fun decodeBase64Url(encoded: String): String {
        val decoded = Base64.getUrlDecoder().decode(encoded)
        return String(decoded)
    }

    private fun verifySignature(header: String, payload: String, signature: String, keyId: String): Boolean {
        return try {
            val publicKey = getApplePublicKey(keyId)
            val signatureInstance = Signature.getInstance(SIGNATURE_ALGORITHM)
            signatureInstance.initVerify(publicKey)
            signatureInstance.update("$header.$payload".toByteArray())

            val signatureBytes = Base64.getUrlDecoder().decode(signature)
            signatureInstance.verify(signatureBytes)
        } catch (e: Exception) {
            false
        }
    }

    private fun getApplePublicKey(keyId: String): PublicKey {
        // Get Apple's public keys
        val response = restTemplate.getForObject(APPLE_KEYS_URL, Map::class.java)
        val keys = response?.get(FIELD_KEYS) as? List<Map<String, Any>>
            ?: throw IllegalStateException("Unable to fetch Apple public keys")

        // Find the key with matching key ID
        val key = keys.find { it[FIELD_KID] == keyId }
            ?: throw IllegalStateException("Unable to find matching key")

        // Extract RSA components
        val n = key[FIELD_N] as String
        val e = key[FIELD_E] as String

        // Decode Base64URL encoded values
        val modulus = BigInteger(1, Base64.getUrlDecoder().decode(n))
        val exponent = BigInteger(1, Base64.getUrlDecoder().decode(e))

        // Create RSA public key
        val keySpec = RSAPublicKeySpec(modulus, exponent)
        val keyFactory = KeyFactory.getInstance(RSA_ALGORITHM)
        return keyFactory.generatePublic(keySpec)
    }
}
