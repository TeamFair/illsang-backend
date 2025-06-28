package com.illsang.management.application.command

data class UpdateImageCommand(
    val id: Long,
    val type: String? = null,
    val name: String? = null,
    val size: Long? = null
) 