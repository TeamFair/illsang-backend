package com.teamfair.moduleuser.application.command

import com.teamfair.moduleuser.domain.model.UserStatus
import java.util.*

data class UpdateUserCommand(
    val id: Long,
    val email: String? = null,
    val channel: String? = null,
    val nickname: String? = null,
    val status: UserStatus? = null,
    val profileImageId: UUID? = null
) 