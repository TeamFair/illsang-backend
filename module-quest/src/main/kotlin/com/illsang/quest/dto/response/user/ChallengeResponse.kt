package com.illsang.quest.dto.response.user

import com.illsang.quest.domain.model.quset.QuizModel
import com.illsang.quest.domain.model.user.ChallengeModel

data class ChallengeResponse(
    val challengeId: Long,
) {
    companion object {
        fun from(challenge: ChallengeModel): ChallengeResponse {
            return ChallengeResponse(
                challengeId = challenge.id
            )
        }
    }
}

data class RandomQuizResponse(
    val id: Long,
    val question: String,
    val hint: String? = null,
) {
    companion object {
        fun from(quiz: QuizModel): RandomQuizResponse {
            return RandomQuizResponse(
                id = quiz.id!!,
                question = quiz.question,
                hint = quiz.hint,
            )
        }
    }
}
