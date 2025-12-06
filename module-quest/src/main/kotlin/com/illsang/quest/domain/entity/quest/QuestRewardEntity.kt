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
    @JoinColumn(name = "quest_id")
    var quest: QuestEntity,

    @Column(name = "reward_type")
    @Enumerated(EnumType.STRING)
    var rewardType: RewardType,

    @Column(name = "point_type")
    @Enumerated(EnumType.STRING)
    var pointType: PointType = PointType.NONE,

    @Column(name = "reward_value")
    var point: Int,

    @Column(name= "coupon_id")
    var couponId: Long? = null

) : BaseEntity() {

    fun update(request: QuestRewardUpdateRequest) {
        this.rewardType = request.rewardType
        this.pointType = request.pointType
        this.point = request.point
        this.couponId = request.couponId
    }

}
