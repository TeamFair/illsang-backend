package com.illsang.management.application.command

data class CreateImageCommand(
    val type: String,
    val name: String,
    val size: Long
) 