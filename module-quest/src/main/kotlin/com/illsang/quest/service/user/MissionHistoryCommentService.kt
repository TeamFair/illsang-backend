package com.illsang.quest.service.user

import com.illsang.common.event.user.info.UserInfoGetEvent
import com.illsang.quest.domain.entity.user.UserMissionHistoryCommentEntity
import com.illsang.quest.dto.request.user.MissionHistoryCommentRequest
import com.illsang.quest.dto.request.user.MissionHistoryCommentUpdateRequest
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

    fun getMissionHistoryComment(userMissionHistoryId: Long): List<MissionHistoryCommentResponse> {
        val comments = missionHistoryCommentRepository.findAllByMissionHistoryId(userMissionHistoryId)
            .map { comment ->
                MissionHistoryCommentResponse.from(

                    run {
                        val usersEvent = UserInfoGetEvent(listOf(comment.writerId))
                        eventPublisher.publishEvent(usersEvent)
                        usersEvent.response.find { it.userId == comment.writerId }!!
                    },
                    comment
                )
            }
            .let { commentList ->
                val commentMap = commentList.associateBy { it.id }
                commentList.forEach { comment ->
                    comment.parentId?.let { parentId ->
                        commentMap[parentId]?.children?.add(comment)
                    }
                }
                commentList.filter { it.parentId == null }
            }

        return comments
    }

    @Transactional
    fun createComment(
        request: MissionHistoryCommentRequest,
        userMissionHistoryId: Long,
    ){
        val missionHistory = missionHistoryService.getMissionHistoryById(userMissionHistoryId)
        request.toEntity(missionHistory).let { missionHistoryCommentRepository.save(it) }
    }

    @Transactional
    fun deleteComment(id: Long){
        missionHistoryCommentRepository.deleteById(id)
    }

    @Transactional
    fun updateComment(id: Long, request: MissionHistoryCommentUpdateRequest){
        val userMissionHistoryComment = findById(id)
        userMissionHistoryComment.update(request)
    }

    private fun findById(id:Long): UserMissionHistoryCommentEntity =
        this.missionHistoryCommentRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("comment id not found")
}