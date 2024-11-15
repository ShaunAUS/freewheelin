package com.example.freewheelin.enums

enum class ProblemType(
    val value: String,
    val number: Int
) {
    SUBJECTION("주관식", 0),
    SELECTION("객관식", 1),
    ALL("전체", 2);

    companion object {
        fun toInt(problemType: ProblemType?): Int? {
            return problemType?.let { entries.first { it.value == problemType.value }.number }
        }

        fun toEnum(number: Int): ProblemType {
            return entries.first { it.number == number }
        }
    }
}
