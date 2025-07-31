package com.illsang.quest.domain.entity.quest

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.dto.request.quest.QuestUpdateRequest
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "quest")
class QuestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(name = "image_id")
    var imageId: String? = null,

    @Column(name = "writer_name", length = 100)
    var writerName: String,

    @Column(name = "main_image_id")
    var mainImageId: String? = null,

    @Column(name = "popular_yn")
    var popularYn: Boolean = false,

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    var type: QuestType,

    @Column(name = "repeat_frequency", length = 50)
    @Enumerated(EnumType.STRING)
    var repeatFrequency: QuestRepeatFrequency? = null,

    @Column(name = "sort_order")
    var sortOrder: Int = 0,

    @Column(name = "expire_date")
    var expireDate: LocalDateTime? = null,

    @Column(name = "banner_id")
    var bannerId: Long? = null,

    @Column(name = "commercial_area_code")
    var commercialAreaCode: String,

    @Column(name = "use_yn", nullable = false)
    var useYn: Boolean = false,

    @OneToMany(mappedBy = "quest", cascade = [CascadeType.ALL], orphanRemoval = true)
    val missions: MutableList<MissionEntity> = mutableListOf(),

    @OneToMany(mappedBy = "quest", cascade = [CascadeType.ALL], orphanRemoval = true)
    val rewards: MutableList<QuestRewardEntity> = mutableListOf(),
) : BaseEntity() {

    init {
        validateQuestType()
    }

    fun addMission(mission: MissionEntity) {
        missions.add(mission)
    }

    fun update(request: QuestUpdateRequest) {
        this.title = request.title
        this.type = request.type
        this.repeatFrequency = request.repeatFrequency
        this.imageId = request.imageId
        this.mainImageId = request.mainImageId
        this.popularYn = request.popularYn
        this.sortOrder = request.sortOrder
        this.writerName = request.writerName
        this.expireDate = request.expireDate
        this.bannerId = request.bannerId
        this.commercialAreaCode = request.commercialAreaCode
        this.useYn = request.useYn

        this.validateQuestType()
    }

    private fun validateQuestType() {
        if (this.type == QuestType.REPEAT) {
            requireNotNull(this.repeatFrequency)
        }
    }

}
