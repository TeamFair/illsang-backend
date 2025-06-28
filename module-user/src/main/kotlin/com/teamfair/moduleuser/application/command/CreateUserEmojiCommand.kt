package com.teamfair.moduleuser.application.command

data class CreateUserEmojiCommand(
    val userId: Long,
    val emojiId: Long,
    val isEquipped: Boolean = false
) 