package com.illsang.quest.dto.response.history

import com.illsang.quest.domain.model.history.ChallengeModel

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
