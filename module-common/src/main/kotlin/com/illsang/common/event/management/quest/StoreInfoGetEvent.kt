package com.illsang.common.event.management.quest

data class StoreInfoGetEvent(
    val storeId: Long?
) {
    lateinit var response: StoreInfo

    class StoreInfo(
        val storeName: String?,
        val imageId: String?,
        val address: String?,
        val phoneNumber: String?,
        val description: String?
    )
}