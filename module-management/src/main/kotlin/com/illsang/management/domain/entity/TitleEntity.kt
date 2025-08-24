package com.illsang.management.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType
import com.illsang.management.dto.request.TitleRequest
import jakarta.persistence.*

@Entity
@Table(name = "title")
class TitleEntity(
    @Id
    val id: String,

    @Column(name = "condition")
    var condition: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    var grade: TitleGrade,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var type: TitleType,

    @Column(name = "use_yn")
    var useYn: Boolean = false,
) : BaseEntity() {

    fun update(request: TitleRequest) {
        this.name = request.name;
        this.grade = request.grade;
        this.useYn = request.useYn;
        this.type = request.type;
        this.condition = request.condition;
    }
}
