package com.illsang.management.listener

import com.illsang.common.event.management.store.StoreExistOrThrowEvent
import com.illsang.common.event.management.store.StoreInfoGetEvent
import com.illsang.management.service.StoreService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class StoreEventListener(
    val storeService: StoreService,
) {

    @EventListener
    fun getStoreInfo(event: StoreInfoGetEvent){
        val store = event.storeId?.let { storeService.getStore(it) }

        event.response = StoreInfoGetEvent.StoreInfo(
            storeName = store?.name,
            imageId =  store?.imageId,
            address = store?.address,
            phoneNumber = store?.phoneNumber,
            description = store?.description,
        )
    }

    @EventListener
    fun existOrThrowStore(event: StoreExistOrThrowEvent){
        storeService.findById(event.storeId)
    }
}