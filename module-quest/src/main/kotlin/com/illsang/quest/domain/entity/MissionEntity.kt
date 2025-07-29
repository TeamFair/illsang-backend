package com.illsang.quest.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.dto.request.MissionUpdateRequest
import com.illsang.quest.enums.MissionType
import jakarta.persistence.*

@Entity
@Table(name = "mission")
class MissionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    var type: MissionType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id", nullable = false)
    var quest: QuestEntity,

    @Column(name = "sort_order")
    var sortOrder: Int = 0,

    @OneToMany(mappedBy = "mission", cascade = [CascadeType.ALL], orphanRemoval = true)
    val quizzes: MutableList<QuizEntity> = mutableListOf(),
) : BaseEntity() {

    init {
        this.quest.addMission(this)
        if (requiredQuiz()) {
            if (this.quizzes.isEmpty()) {
                throw IllegalArgumentException("There are no quizzes for this $type quest")
            }
        }
    }


    fun update(request: MissionUpdateRequest) {
        this.type = request.type
        this.sortOrder = request.sortOrder
    }

    fun addQuizzes(quizzes: List<QuizEntity>) {
        this.quizzes.addAll(quizzes)
    }

    fun deleteQuiz(quizId: Long) {
        if (this.canRemoveQuiz()) {
            this.quizzes.removeIf { it.id == quizId }
        } else
            throw IllegalArgumentException("Cannot remove a quiz from the quest. Require at least one quiz.")
    }

    private fun canRemoveQuiz(): Boolean =  !requiredQuiz() || this.quizzes.size > 1
    private fun requiredQuiz(): Boolean = this.type.requireQuiz()

}
