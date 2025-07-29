package com.illsang.user.domain.entity

import com.illsang.auth.enums.OAuthProvider
import com.illsang.common.converter.StringListConverter
import com.illsang.common.entity.BaseEntity
import com.illsang.common.event.management.season.CurrentSeason
import com.illsang.user.enums.UserStatus
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.ZoneId

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(nullable = false, unique = true, length = 100)
    var email: String,

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    var channel: OAuthProvider,

    @Column(nullable = false, length = 25)
    var nickname: String,

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    var status: UserStatus,

    @Column(name = "status_updated_at")
    var statusUpdatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "profile_image_id")
    var profileImageId: String? = null,

    @Column(name = "title_id")
    var titleHistoryId: String? = null,

    @Column(name = "area_zone_code")
    var areaZoneCode: String? = null,

    @Column(name = "area_zone_updated_at")
    var areaZoneUpdatedAt: LocalDateTime?,

    @Column(name = "roles", length = 500)
    @Convert(converter = StringListConverter::class)
    var roles: List<String> = emptyList(),
) : BaseEntity() {

    init {
        validateNickName(nickname)
        require(email.isNotBlank()) { "Email is required" }
        require(email.length <= 100) { "Email must be less than 100 characters" }
        require(nickname.length <= 25) { "Nickname must be less than 25 characters" }
    }

    fun updateNickname(nickName: String) {
        validateNickName(nickname)
        this.nickname = nickName
    }

    private fun validateNickName(nickname: String) {
        require(nickname.isNotBlank()) { "Nickname is required" }
    }

    fun updateProfileImage(imageId: String?) {
        this.profileImageId = imageId
    }

    fun updateTitle(titleHistoryId: String?) {
        if (titleHistoryId.isNullOrBlank()) {
            this.titleHistoryId = null
        } else {
            this.titleHistoryId = titleHistoryId
        }
    }

    fun updateAreaZone(commercialAreaCode: String, currentSeason: CurrentSeason) {
        this.areaZoneUpdatedAt?.let {
          if (!it.isBefore(currentSeason.startDate) && !it.isAfter(currentSeason.endDate)) {
              throw IllegalArgumentException("일상존은 시즌 동안 한번만 업데이트 할 수 있습니다.")
          }
        }

        this.areaZoneCode = commercialAreaCode
        this.areaZoneUpdatedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
    }

}
