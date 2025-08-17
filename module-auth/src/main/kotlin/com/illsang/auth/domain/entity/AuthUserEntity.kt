package com.illsang.auth.domain.entity

import com.illsang.common.converter.StringListConverter
import com.illsang.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class AuthUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(nullable = false, unique = true, length = 100)
    var email: String,

    @Column(name = "roles", length = 500)
    @Convert(converter = StringListConverter::class)
    var roles: List<String> = emptyList(),
) : BaseEntity()
