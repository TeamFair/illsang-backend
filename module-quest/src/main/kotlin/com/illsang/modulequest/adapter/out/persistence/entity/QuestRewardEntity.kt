package com.illsang.modulequest.adapter.out.persistence.entity

import com.illsang.common.adapter.out.persistence.entity.BaseEntity
import com.illsang.modulequest.domain.model.enums.RewardType
import jakarta.persistence.*

@Entity
@Table(name = "quest_reward")
class QuestRewardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id", nullable = false)
    var quest: QuestEntity,

    @Column(name = "reward_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    var type: RewardType,

    @Column(name = "reward_value", nullable = false)
    var amount: Int
) : BaseEntity()
