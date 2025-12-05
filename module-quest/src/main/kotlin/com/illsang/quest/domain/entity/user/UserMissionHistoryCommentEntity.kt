package com.illsang.quest.domain.entity.user

import com.illsang.common.entity.BaseEntity
import com.illsang.common.enums.ReportStatusType
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
import java.time.Duration
import java.time.LocalDateTime

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
    var status: ReportStatusType = ReportStatusType.COMPLETED,

    @Column(name="delete_yn")
    var deleteYn: Boolean = false,

    ) : BaseEntity() {

    fun increaseReportedCount() {
        this.reportedCount++
    }

    fun changeReportStatus(status: ReportStatusType) {
        this.status = status
    }

    fun update(request: MissionHistoryCommentUpdateRequest){
        this.comment = request.comment
    }

    fun delete(userId: String,){
        if(this.writerId != userId) throw IllegalArgumentException("Only writer can delete comment")
        this.deleteYn = true
    }

    fun checkRecentComment(missionHistory: UserMissionHistoryEntity, userId: String) {
        val currentTime = LocalDateTime.now()
        val hasRecentComment = missionHistory.missionHistoryComments.any { comment ->
            comment.writerId == userId && Duration.between(comment.createdAt, currentTime).toMinutes() < 1
        }

        if (hasRecentComment) {
            throw IllegalArgumentException("Cannot create multiple comments within 1 minute")
        }
    }
}