package com.illsang.moduleuser.domain.model

import com.illsang.common.domain.model.BaseModel
import java.time.LocalDateTime
import java.util.*

data class UserModel(
    val id: Long? = null,
    val email: String,
    val channel: String,
    val nickname: String,
    val status: UserStatus,
    val statusUpdatedAt: LocalDateTime? = null,
    val profileImageId: UUID? = null,
    val userXps: List<UserXpModel> = emptyList(),
    val xpHistories: List<UserXpHistoryModel> = emptyList(),
    val emojis: List<UserEmojiModel> = emptyList(),
    override val createdBy: String? = null,
    override val createdAt: LocalDateTime? = null,
    override val updatedBy: String? = null,
    override val updatedAt: LocalDateTime? = null
) : BaseModel(createdBy, createdAt, updatedBy, updatedAt) {
    fun validate() {
        require(email.isNotBlank()) { "Email is required" }
        require(channel.isNotBlank()) { "Channel is required" }
        require(nickname.isNotBlank()) { "Nickname is required" }
        require(email.length <= 100) { "Email must be less than 100 characters" }
        require(channel.length <= 50) { "Channel must be less than 50 characters" }
        require(nickname.length <= 25) { "Nickname must be less than 25 characters" }
    }
}
