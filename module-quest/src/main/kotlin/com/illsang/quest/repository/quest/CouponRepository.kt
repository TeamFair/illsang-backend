package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<CouponEntity, Long>{
    fun findAllByStoreId(storeId: Long): List<CouponEntity>
}