package com.illsang.user.service

import UserCouponRepository
import com.illsang.user.domain.entity.UserCouponEntity
import com.illsang.user.domain.model.UserCouponModel
import com.illsang.user.dto.request.UserCouponCreateRequest
import com.illsang.user.dto.request.UserCouponUpdateRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserCouponService(
    private val userCouponRepository: UserCouponRepository
) {

    fun getById(id: Long): UserCouponModel {
        val entity = findById(id)
        return UserCouponModel.from(entity)
    }

    fun listByUser(userId: Long, page: Int, size: Int): List<UserCouponModel> {
        val pageable = PageRequest.of(page, size)
        return userCouponRepository.findAllByUserId(userId, pageable)
            .content
            .map(UserCouponModel::from)
    }

    @Transactional
    fun create(request: UserCouponCreateRequest): UserCouponModel {
        val saved = userCouponRepository.save(request.toEntity())
        return UserCouponModel.from(saved)
    }

    @Transactional
    fun update(id: Long, request: UserCouponUpdateRequest): UserCouponModel {
        val entity = findById(id)
        entity.update(request)
        return UserCouponModel.from(entity)
    }

    private fun findById(id: Long): UserCouponEntity =
        userCouponRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("UserCoupon not found with id: $id")
}
