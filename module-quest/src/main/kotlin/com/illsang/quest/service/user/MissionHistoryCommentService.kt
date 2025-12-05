package com.illsang.quest.service.user

import com.illsang.common.enums.ReportStatusType
import com.illsang.common.enums.ReportType
import com.illsang.common.event.management.report.ReportCreateEvent
import com.illsang.common.event.management.report.ReportExistEvent
import com.illsang.common.event.user.info.UserInfoGetEvent
import com.illsang.quest.domain.entity.user.UserMissionHistoryCommentEntity
import com.illsang.quest.dto.request.user.MissionHistoryCommentRequest
import com.illsang.quest.dto.request.user.MissionHistoryCommentUpdateRequest
import com.illsang.quest.dto.request.user.ReportMissionHistoryCommentRequest
import com.illsang.quest.dto.response.user.MissionHistoryCommentResponse
import com.illsang.quest.repository.user.MissionHistoryCommentRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MissionHistoryCommentService(
    private val missionHistoryCommentRepository: MissionHistoryCommentRepository,
    private val missionHistoryService: MissionHistoryService,
    private val eventPublisher: ApplicationEventPublisher,
) {

    fun getMissionHistoryComment(userMissionHistoryId: Long, userId: String): List<MissionHistoryCommentResponse> {
        val comments = missionHistoryCommentRepository
            .findAllByMissionHistoryId(userMissionHistoryId)
            .map { comment ->

                val userInfo = getUserInfoSync(comment.writerId)
                val isReported = checkReportSync(comment.id!!, userId)

                MissionHistoryCommentResponse.from(
                    userInfo = userInfo,
                    userMissionHistoryCommentEntity = comment,
                    hasReportedYn = isReported
                )
            }

        // 트리 구성
        val map = comments.associateBy { it.id }
        comments.forEach { c ->
            c.parentId?.let { parentId ->
                map[parentId]?.children?.add(c)
            }
        }

        return comments.filter { it.parentId == null }

    }

    @Transactional
    fun createComment(
        request: MissionHistoryCommentRequest,
        userMissionHistoryId: Long,
        userId: String,
    ) {
        val missionHistory = missionHistoryService.getMissionHistoryById(userMissionHistoryId)
        val newComment = request.toEntity(
            missionHistory,
            userId = userId
        )
        newComment.checkRecentComment(missionHistory, userId)
        missionHistoryCommentRepository.save(newComment)
    }

    @Transactional
    fun deleteComment(id: Long, userId: String) {
        val userMissionHistoryComment = findById(id)
        userMissionHistoryComment.delete(userId)
    }

    @Transactional
    fun updateComment(id: Long, request: MissionHistoryCommentUpdateRequest) {
        val userMissionHistoryComment = findById(id)
        userMissionHistoryComment.update(request)
    }

    @Transactional
    fun reportComment(request: ReportMissionHistoryCommentRequest, userId: String, commentId: Long) {
        val comment = this.findById(commentId)
        val event = ReportCreateEvent(
            targetId = commentId,
            type = ReportType.USER_COMMENT,
            reason = request.reason,
            userId = userId,
        )
        this.eventPublisher.publishEvent(event)
        comment.increaseReportedCount()
    }

    @Transactional
    fun changeReportStatus(commentId: Long, status: ReportStatusType) {
        val comment = this.findById(commentId)
        comment.changeReportStatus(status)
    }

    private fun findById(id: Long): UserMissionHistoryCommentEntity =
        this.missionHistoryCommentRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("comment id not found")

    private fun getUserInfoSync(userId: String): UserInfoGetEvent.UserInfo {
        val event = UserInfoGetEvent(listOf(userId))
        eventPublisher.publishEvent(event)
        return event.response.first { it.userId == userId }
    }

    private fun checkReportSync(targetId: Long, userId: String): Boolean {
        val event = ReportExistEvent(targetId, ReportType.USER_COMMENT, userId)
        eventPublisher.publishEvent(event)
        return event.response
    }
}