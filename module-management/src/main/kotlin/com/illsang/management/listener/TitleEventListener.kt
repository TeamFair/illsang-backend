package com.illsang.management.listener

import com.illsang.common.event.user.title.GetTitleInfoEvent
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

        event.response = GetTitleInfoEvent.TitleInfo(
            titleId = title.id,
            titleName = title.name,
            titleGrade = title.grade,
            titleType = title.type,
        )
    }
}

