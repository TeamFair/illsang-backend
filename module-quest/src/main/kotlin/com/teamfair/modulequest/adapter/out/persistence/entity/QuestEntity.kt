package com.teamfair.modulequest.adapter.out.persistence.entity

import com.illsang.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "quest")
class QuestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "image_url", length = 512)
    var imageUrl: String? = null,

    @Column(name = "is_repeatable")
    var isRepeatable: Boolean = false,

    @Column(name = "is_published")
    var isPublished: Boolean = false,

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