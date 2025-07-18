package com.teamfair.moduleuser.application.command

import com.teamfair.moduleuser.domain.model.UserStatus
import java.util.*

data class CreateUserCommand(
    val email: String,
    val channel: String,
    val nickname: String,
    val status: UserStatus = UserStatus.ACTIVE,
    val profileImageId: UUID? = null
) 