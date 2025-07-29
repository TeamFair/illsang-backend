package com.illsang.quest.enums

enum class MissionType {
    FREE,
    NORMAL,
    OX,
    WORDS,
    ;

    fun requireQuiz(): Boolean {
        return this == OX || this == WORDS
    }

}
