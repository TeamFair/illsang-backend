package com.illsang.quest.service.quest

import com.illsang.quest.domain.entity.quest.StoreEntity
import com.illsang.quest.domain.model.quset.StoreModel
import com.illsang.quest.dto.request.quest.StoreCreateRequest
import com.illsang.quest.dto.request.quest.StoreUpdateRequest
import com.illsang.quest.repository.quest.StoreRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StoreService(
    private val storeRepository: StoreRepository,
) {
    fun findById(id: Long): StoreEntity =
        storeRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("Store not found with id: $id")


    fun getStore(id: Long): StoreModel{
        val store = this.findById(id)
        return StoreModel.from(store)
    }

    fun getStoreList(pageable: Pageable): Page<StoreModel> {
        val stores = storeRepository.findAll(pageable)
        return stores.map { StoreModel.from(it) }
    }

    @Transactional
    fun create(store: StoreCreateRequest): StoreModel {
        val store = storeRepository.save(store.toEntity())
        return StoreModel.from(store)
    }

    @Transactional
    fun update(id: Long, store: StoreUpdateRequest): StoreModel {
        val existingStore = findById(id)
        existingStore.update(
            name = store.name,
            address = store.address,
            phoneNumber = store.phoneNumber,
            description = store.description,
            activeYn = store.activeYn,
            imageId = store.imageId,
            managerId = store.managerId,
        )

        return StoreModel.from(existingStore)
    }

    @Transactional
    fun delete(id: Long) {
        val store = findById(id)
        storeRepository.delete(store)
    }
}