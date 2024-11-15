package com.example.freewheelin.enums

enum class Level(
    val value: String,
    val number: Int
) {

    LOW("하", 0),
    MIDDLE("중", 1),
    HIGH("상", 2),
    ;

    companion object {
        fun toInt(level: Level?): Int? {
            return level?.let { entries.first { it.value == level.value }.number }
        }

        fun toEnum(number: Int): String {
            return entries.first { it.number == number }.value
        }
    }
}