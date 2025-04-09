package com.teamfair.illsang.core.quest.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.teamfair.illsang.core.common.querydsl.PaginationSortRepository

class QuestCustomRepositoryImpl(
    private val query: JPAQueryFactory,
) : QuestCustomRepository, PaginationSortRepository {
}
