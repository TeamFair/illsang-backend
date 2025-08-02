package com.illsang.quest.enums

enum class MissionType {
    PHOTO,
    OX,
    WORDS,
    ;

    fun requireQuiz(): Boolean {
        return this == OX || this == WORDS
    }

}
