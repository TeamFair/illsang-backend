package com.illsang.management.listener

import com.illsang.common.event.quest.GetTitleInfoEvent
import com.illsang.management.domain.model.TitleModel
import com.illsang.management.service.TitleService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class TitleEventListener(
    private val titleService: TitleService
) {
    @EventListener
    fun getTitleInfo(event: GetTitleInfoEvent) {
        val title = titleService.getTitle(event.titleId)
        event.titleId = title.id
        event.titleName = title.name
        event.titleType = title.type
        event.titleGrade = title.grade
    }
}

