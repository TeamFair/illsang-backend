package com.illsang.quest.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.dto.request.QuestUpdateRequest
import com.illsang.quest.enums.QuestRepeatFrequency
import com.illsang.quest.enums.QuestType
import jakarta.persistence.*

@Entity
@Table(name = "quest")
class QuestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

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

    @OneToMany(mappedBy = "quest", cascade = [CascadeType.ALL], orphanRemoval = true)
    val missions: MutableList<MissionEntity> = mutableListOf(),

    @OneToMany(mappedBy = "quest", cascade = [CascadeType.ALL], orphanRemoval = true)
    val rewards: MutableList<QuestRewardEntity> = mutableListOf(),
) : BaseEntity() {

    init {
        validateQuestType(this.type, this.repeatFrequency)
    }

    fun addMission(mission: MissionEntity) {
        missions.add(mission)
    }

    fun update(request: QuestUpdateRequest) {
        this.type = request.type
        this.repeatFrequency = request.repeatFrequency

        this.validateQuestType(this.type, this.repeatFrequency)

        this.imageId = request.imageId
        this.mainImageId = request.mainImageId
        this.popularYn = request.popularYn
        this.sortOrder = request.sortOrder
        this.writerName = request.writerName
    }

    private fun validateQuestType(questType: QuestType, repeatFrequency: QuestRepeatFrequency?) {
        if (questType == QuestType.REPEAT) {
            requireNotNull(repeatFrequency)
        }
    }

}
