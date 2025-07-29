package com.illsang.user.service

import com.illsang.common.event.management.image.ImageDeleteEvent
import com.illsang.user.domain.entity.UserEntity
import com.illsang.user.domain.model.UserModel
import com.illsang.user.dto.request.CreateUserRequest
import com.illsang.user.dto.request.UpdateUserNickNameRequest
import com.illsang.user.dto.request.UpdateUserProfileImageRequest
import com.illsang.user.repository.UserRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val eventPublisher: ApplicationEventPublisher,
    private val userRepository: UserRepository,
) {

    fun getUser(userId: String): UserModel {
        val user = this.findById(userId)

        return UserModel.from(user)
    }

    fun getUserByEmail(email: String): UserModel {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("User not found with email: $email")

        return UserModel.from(user)
    }

    fun getUserByNickname(nickname: String): UserModel? {
        val user = userRepository.findByNickname(nickname)

        return user?.let {
            UserModel.from(it)
        }
    }

    @Transactional
    fun createUser(userRequest: CreateUserRequest): UserModel {
        val existsByEmail = this.userRepository.existsByEmail(userRequest.email)
        if (existsByEmail) {
            throw IllegalArgumentException("Email already exists")
        }

        val existsByNickname = this.getUserByNickname(userRequest.nickname)
        if (existsByNickname != null) {
            throw IllegalArgumentException("Nickname already exists")
        }

        val user = this.userRepository.save(userRequest.toEntity())
        return UserModel.from(user)
    }

    @Transactional
    fun updateNickname(userId: String, request: UpdateUserNickNameRequest): UserModel {
        val user = this.findById(userId)

        val existsByNickname = this.getUserByNickname(request.nickName)
        if (existsByNickname != null) {
            throw IllegalArgumentException("Nickname already exists")
        }

        user.updateNickname(request.nickName)

        return UserModel.from(user)
    }

    @Transactional
    fun updateProfileImage(userId: String, request: UpdateUserProfileImageRequest): UserModel {
        val user = this.findById(userId)

        val prevProfileImage = user.profileImageId

        user.updateProfileImage(request.imageId)
        if (request.imageId == null && prevProfileImage != null) {
            this.eventPublisher.publishEvent(ImageDeleteEvent(
                imageId =  prevProfileImage
            ))
        }

        return UserModel.from(user)
    }

    @Transactional
    fun updateTitle(userId: String, titleHistoryId: String?): UserModel {
        val user = this.findById(userId)

        user.updateTitle(titleHistoryId)

        return UserModel.from(user)
    }

    @Transactional
    fun delete(userId: String) {
        val user = this.findById(userId)

        this.userRepository.delete(user)
    }

    private fun findById(id: String): UserEntity {
        return userRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("User not found with id: $id")
    }

}
