package com.illsang.user.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType
import jakarta.persistence.*

@Entity
@Table(name = "user_title")
class UserTitleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    @Column(name = "title_id")
    var titleId: String,

    @Column(name = "title_name")
    var titleName: String,

    @Column(name = "title_grade")
    @Enumerated(EnumType.STRING)
    var titleGrade: TitleGrade,

    @Column(name = "title_type")
    @Enumerated(EnumType.STRING)
    var titleType: TitleType,

    @Column(name = "read_yn")
    var readYn: Boolean = false,
) : BaseEntity(){
    fun createTitle(titleType: TitleType, titleGrade: TitleGrade, titleName: String){
        readYn = false
        this.titleType = titleType
        this.titleGrade = titleGrade
        this.titleName = titleName
    }

    fun readTitle(){
        readYn = true
    }
}
