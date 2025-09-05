package com.illsang.quest.listener

import com.illsang.common.event.management.quest.StoreInfoGetEvent
import com.illsang.quest.service.quest.StoreService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class StoreEventListener(
    val storeService: StoreService,
) {

    @EventListener
    fun getStoreInfo(event: StoreInfoGetEvent){
        val store = storeService.getStore(event.storeId!!)

        event.response = StoreInfoGetEvent.StoreInfo(
            storeName = store.name,
            imageId =  store.imageId!!,
            address = store.address!!,
            phoneNumber = store.phoneNumber!!,
            description = store.description,
        )
    }
}