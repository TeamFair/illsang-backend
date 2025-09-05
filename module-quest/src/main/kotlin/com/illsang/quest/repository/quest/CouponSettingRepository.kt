package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.CouponSettingEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CouponSettingRepository : JpaRepository<CouponSettingEntity, Long>{

}