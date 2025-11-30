package com.illsang.quest.domain.entity.user

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.dto.request.user.MissionHistoryCommentRequest
import com.illsang.quest.dto.request.user.MissionHistoryCommentUpdateRequest
import com.illsang.quest.enums.MissionHistoryCommentStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user_mission_history_comment")
class UserMissionHistoryCommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "parent_id")
    var parentId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_mission_history_id")
    val missionHistory: UserMissionHistoryEntity,

    @Column(name = "comment")
    var comment: String,

    @Column(name = "writer_id")
    val writerId: String,

    @Column(name = "reported_count")
    var reportedCount: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: MissionHistoryCommentStatus = MissionHistoryCommentStatus.SUBMITTED,

    ) : BaseEntity() {

    fun increaseReportedCount() {
        this.reportedCount++
    }

    fun changeStatusReported() {
        this.status = MissionHistoryCommentStatus.REPORTED
    }

    fun update(request: MissionHistoryCommentUpdateRequest){
        this.comment = request.comment
    }

}