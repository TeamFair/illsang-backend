package com.teamfair.illsang.core.quest.entity

import com.teamfair.illsang.core.common.entity.BaseEntity
import com.teamfair.illsang.core.quest.enumeration.QuestRepeatFrequency
import com.teamfair.illsang.core.quest.enumeration.QuestType
import jakarta.persistence.*

@Entity
@Table(name = "quest")
class Quest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    var imageId: Long,
    @Column(nullable = true)
    var mainImageId: Long? = null,
    @Column(nullable = false)
    var writerName: String,
    @Column(nullable = false)
    var popularYn: Boolean,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var questType: QuestType,
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    var repeatFrequency: QuestRepeatFrequency?,
    @Column(nullable = false)
    var sortOrder: Long = 0,

): BaseEntity()
