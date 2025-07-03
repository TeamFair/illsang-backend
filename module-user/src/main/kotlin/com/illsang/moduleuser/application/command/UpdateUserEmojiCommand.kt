package com.illsang.moduleuser.application.command

data class UpdateUserEmojiCommand(
    val id: Long,
    val userId: Long,
    val emojiId: Long,
    val isEquipped: Boolean,
    val targetId: Long
)
