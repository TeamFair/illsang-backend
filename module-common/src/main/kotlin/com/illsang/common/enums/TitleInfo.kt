package com.illsang.common.enums

enum class TitleInfo(
    val titleId: String,
    val titleName: String,
    val titleGrade: TitleGrade,
    val titleType: TitleType
) {
    TITLE_2025_DAILY_PIONEER("TQ00001", "2025년 일상의 개척자", TitleGrade.STANDARD, TitleType.METRO),
    TITLE_FIRST_STEP("TQ00002", "일상의 첫 걸음", TitleGrade.STANDARD, TitleType.METRO),
    TITLE_ONE_DAY("TQ00003", "첫 걸음", TitleGrade.STANDARD, TitleType.METRO),
    TITLE_FOUR_DAYS("TQ00004", "불씨를 지핀 자", TitleGrade.STANDARD, TitleType.METRO),
    TITLE_SEVEN_DAYS("TQ00005", "성실한 모험가", TitleGrade.STANDARD, TitleType.METRO),
    TITLE_FOURTEEN_DAYS("TQ00006", "의지의 탐험가", TitleGrade.STANDARD, TitleType.METRO),
    TITLE_THIRTY_DAYS("TQ00007", "일상의 지배자", TitleGrade.STANDARD, TitleType.METRO),
    TITLE_SIXTY_DAYS("TQ00008", "끝없는 정진", TitleGrade.RARE, TitleType.METRO),
    TITLE_ONE_TWENTY_DAYS("TQ00009", "불굴의 개척자", TitleGrade.RARE, TitleType.METRO),
    TITLE_TWO_FORTY_DAYS("TQ00010", "전설을 쓰는 자", TitleGrade.RARE, TitleType.METRO),
    TITLE_360_DAYS("TQ00011", "👑 영원의 구도자", TitleGrade.LEGEND, TitleType.METRO),
    TITLE_METRO_1("TQ00012", "⭐️(광역시/상권)의 전설⭐️", TitleGrade.LEGEND, TitleType.METRO),
    TITLE_METRO_2("TQ00013", "(광역시/상권)의 빛", TitleGrade.LEGEND, TitleType.METRO),
    TITLE_METRO_3("TQ00014", "(광역시/상권)의 친구", TitleGrade.LEGEND, TitleType.METRO),
    TITLE_METRO_4_TO_10("TQ00015", "(광역시/상권)의 발걸음", TitleGrade.RARE, TitleType.METRO),
    TITLE_CONTRIB_1("TQ00016", "지역활성화의 전설", TitleGrade.LEGEND, TitleType.CONTRIBUTION),
    TITLE_CONTRIB_2("TQ00017", "지역활성화의 빛", TitleGrade.LEGEND, TitleType.CONTRIBUTION),
    TITLE_CONTRIB_3("TQ00018", "지역활성화의 친구", TitleGrade.LEGEND, TitleType.CONTRIBUTION),
    TITLE_CONTRIB_4_TO_10("TQ00019", "지역활성화의 발걸음", TitleGrade.RARE, TitleType.CONTRIBUTION);
}