package com.illsang.management.repository

import com.illsang.management.domain.entity.StoreEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<StoreEntity, Long> {

}