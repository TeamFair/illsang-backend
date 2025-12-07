package com.illsang.quest.domain.entity.quest

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.dto.request.quest.MissionUpdateRequest
import com.illsang.quest.enums.MissionType
import jakarta.persistence.*

@Entity
@Table(name = "mission")
class MissionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title")
    var title: String,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var type: MissionType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    var quest: QuestEntity,

    @Column(name = "sort_order")
    var sortOrder: Int = 0,

    @OneToMany(mappedBy = "mission", cascade = [CascadeType.ALL], orphanRemoval = true)
    val quizzes: MutableList<QuizEntity> = mutableListOf(),

    @OneToMany(mappedBy = "mission", cascade = [CascadeType.ALL], orphanRemoval = true)
    val labels: MutableList<MissionLabelEntity> = mutableListOf(),
) : BaseEntity() {

    fun update(request: MissionUpdateRequest) {
        this.title = request.title
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

    fun addMissions(labels: List<MissionLabelEntity>) {
        this.labels.addAll(labels)
    }

    private fun canRemoveQuiz(): Boolean =  !requiredQuiz() || this.quizzes.size > 1
    private fun requiredQuiz(): Boolean = this.type.requireQuiz()

}
