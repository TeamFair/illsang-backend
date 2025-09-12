package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CouponRepository : JpaRepository<CouponEntity, Long>{
    @Query("select c from CouponEntity c where c.storeId = :storeId")
    fun findAllByStoreId(storeId: Long?): List<CouponEntity>
    fun existsByImageId(imageId: String): Boolean
}