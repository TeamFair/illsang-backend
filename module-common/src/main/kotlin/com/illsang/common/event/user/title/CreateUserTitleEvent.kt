package com.illsang.common.event.user.title

data class CreateUserTitleEvent (

    val userId : String,
    val userTitleId : Long,
)