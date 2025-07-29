package com.illsang.management.repository

import com.illsang.management.domain.entity.CommercialAreaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommercialAreaRepository : JpaRepository<CommercialAreaEntity, String>
