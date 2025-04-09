package com.teamfair.illsang.core.common.querydsl

import com.querydsl.core.types.Expression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathBuilder
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface PaginationSortRepository {
    fun getOrderSpecifiers(sort: Sort, classType: EntityPathBase<*>, isNullFirst: Boolean): Array<OrderSpecifier<*>> {
        val orders: MutableList<OrderSpecifier<*>> = ArrayList()

        sort.stream().forEach { order: Sort.Order ->
            val direction = if (order.isAscending) Order.ASC else Order.DESC
            val orderByExpression: PathBuilder<*> = PathBuilder(classType.type, classType.metadata)
            val orderSpecifier = OrderSpecifier(direction, orderByExpression[order.property] as Expression<Comparable<*>>)
            if (isNullFirst) orderSpecifier.nullsFirst()
            orders.add(orderSpecifier)
        }
        return orders.toTypedArray()
    }

    fun getOffset(pageable: Pageable?): Long {
        return if (pageable != null && pageable.isPaged) {
            pageable.offset
        } else {
            0
        }
    }

    fun getLimit(pageable: Pageable?): Long {
        return if (pageable != null && pageable.isPaged) {
            pageable.pageSize.toLong()
        } else {
            Long.MAX_VALUE
        }
    }
}
