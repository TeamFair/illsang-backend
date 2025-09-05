package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.StoreEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<StoreEntity, Long>{

}