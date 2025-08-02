package com.illsang.quest.domain.entity.quest

import com.illsang.common.entity.BaseEntity
import com.illsang.common.enums.PointType
import com.illsang.quest.dto.request.quest.QuestRewardUpdateRequest
import com.illsang.quest.enums.RewardType
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
    var rewardType: RewardType,

    @Column(name = "point_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    var pointType: PointType? = null,

    @Column(name = "reward_value", nullable = false)
    var point: Int
) : BaseEntity() {

    fun update(request: QuestRewardUpdateRequest) {
        this.rewardType = request.rewardType
        this.pointType = request.pointType
        this.point = request.point
    }

}
