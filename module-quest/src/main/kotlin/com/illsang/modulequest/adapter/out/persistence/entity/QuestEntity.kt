package com.illsang.modulequest.adapter.out.persistence.entity

import com.illsang.common.adapter.out.persistence.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "quest")
class QuestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "image_id")
    var imageId: Long? = null,

    @Column(name = "writer_name", length = 100)
    var writerName: String? = null,

    @Column(name = "main_image_id")
    var mainImageId: Long? = null,

    @Column(name = "popular_yn")
    var popularYn: Boolean = false,

    @Column(nullable = false, length = 50)
    var type: String,

    @Column(name = "repeat_frequency", length = 50)
    var repeatFrequency: String? = null,

    @Column(name = "sort_order")
    var sortOrder: Int = 0,

    @OneToMany(mappedBy = "quest", cascade = [CascadeType.ALL], orphanRemoval = true)
    val missions: MutableList<MissionEntity> = mutableListOf(),

    @OneToMany(mappedBy = "quest", cascade = [CascadeType.ALL], orphanRemoval = true)
    val rewards: MutableList<QuestRewardEntity> = mutableListOf(),

    @OneToMany(mappedBy = "quest", cascade = [CascadeType.ALL])
    val userHistories: MutableList<UserQuestHistoryEntity> = mutableListOf()
) : BaseEntity() {

    fun addMission(mission: MissionEntity) {
        missions.add(mission)
        mission.quest = this
    }

    fun addReward(reward: QuestRewardEntity) {
        rewards.add(reward)
        reward.quest = this
    }
}
