package com.illsang.management.repository

import com.illsang.management.domain.entity.MetroAreaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MetroAreaRepository : JpaRepository<MetroAreaEntity, String>
