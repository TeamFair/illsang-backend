package com.illsang.user.domain.entity

import com.illsang.auth.enums.OAuthProvider
import com.illsang.common.converter.StringListConverter
import com.illsang.common.entity.BaseEntity
import com.illsang.common.event.management.season.SeasonGetCurrentEvent
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

    @Column(name = "email")
    var email: String,

    @Column(name = "channel")
    @Enumerated(EnumType.STRING)
    var channel: OAuthProvider,

    @Column(name = "nickname")
    var nickname: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: UserStatus,

    @Column(name = "status_updated_at")
    var statusUpdatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "profile_image_id")
    var profileImageId: String? = null,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "current_title_id")
    var currentTitle: UserTitleEntity? = null,

    @Column(name = "commercial_area_code")
    var commercialAreaCode: String? = null,

    @Column(name = "commercial_area_updated_at")
    var commercialAreaUpdatedAt: LocalDateTime? = null,

    @Column(name = "roles")
    @Convert(converter = StringListConverter::class)
    var roles: List<String> = emptyList(),

    @OneToMany(mappedBy = "id.user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userPoints: MutableSet<UserPointEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userTitles: MutableSet<UserTitleEntity> = mutableSetOf(),
) : BaseEntity() {

    init {
        validateNickName(nickname)
        require(email.isNotBlank()) { "Email is required" }
        require(email.length <= 100) { "Email must be less than 100 characters" }
        require(nickname.length <= 25) { "Nickname must be less than 25 characters" }
    }

    fun updateNickname(nickName: String) {
        validateNickName(nickName)
        this.nickname = nickName
    }

    fun updateProfileImage(imageId: String?) {
        this.profileImageId = imageId
    }

    fun updateTitle(userTitle: UserTitleEntity?) {
        this.currentTitle = userTitle
    }

    fun updateAreaZone(commercialAreaCode: String, currentSeason: SeasonGetCurrentEvent.CurrentSeason) {
        this.commercialAreaUpdatedAt?.let {
          if (!it.isBefore(currentSeason.startDate) && !it.isAfter(currentSeason.endDate)) {
              throw IllegalArgumentException("일상존은 시즌 동안 한번만 업데이트 할 수 있습니다.")
          }
        }

        this.commercialAreaCode = commercialAreaCode
        this.commercialAreaUpdatedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
    }

    fun addPoints(userPoints: List<Pair<UserPointKey, Int>>) {
        userPoints.forEach { (key, point) ->
            this.userPoints.find {
                it.id.user.id == key.user.id && it.id.pointType == key.pointType && it.id.metroAreaCode == key.metroAreaCode && it.id.commercialAreaCode == key.commercialAreaCode && it.id.seasonId == key.seasonId
            }?.addPoint(point)
                ?: this.userPoints.add(UserPointEntity(key, point))
        }
    }

    private fun validateNickName(nickname: String) {
        require(nickname.isNotBlank()) { "Nickname is required" }
    }

}
