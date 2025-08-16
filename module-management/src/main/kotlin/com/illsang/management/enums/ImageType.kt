package com.illsang.management.enums

enum class ImageType(
    val prefix: String,
    val description: String,
) {
    BUSINESS_REGISTRATION_CERTIFICATE(
        prefix = "BU",
        description = "영업신고증",
    ),
    ID_CARD(
        prefix = "ID",
        description = "신분증",
    ),
    MARKET_LOGO(
        prefix = "MA",
        description = "마켓-로고",
    ),
    RECEIPT(
        prefix = "RE",
        description = "영수증",
    ),
    QUEST_IMAGE(
        prefix = "QU",
        description = "퀘스트-이미지",
    ),
    BANNER_IMAGE(
        prefix = "BN",
        description = "베너이미지",
    ),
    USER_PROFILE_IMAGE(
        prefix = "UP",
        description = "유저프로필-이미지",
    ),
    COUPON_IMAGE(
        prefix = "CP",
        description = "쿠폰 이미지"
    )
}
