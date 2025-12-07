package com.illsang.quest.domain.entity.quest

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.dto.request.quest.MissionLabelUpdateRequest
import com.illsang.quest.enums.MissionLabelType
import jakarta.persistence.*

@Entity
@Table(name = "mission_label")
class MissionLabelEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    var mission: MissionEntity,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var type: MissionLabelType,

    @Column(name = "label")
    var label: String,
) : BaseEntity() {

    fun update(request: MissionLabelUpdateRequest) {
        this.type = request.type
        this.label = request.label
    }

}
