package com.illsang.common.domain.model

import java.time.LocalDateTime

abstract class BaseModel(
    open val createdBy: String? = null,
    open val createdAt: LocalDateTime? = null,
    open val updatedBy: String? = null,
    open val updatedAt: LocalDateTime? = null
)
