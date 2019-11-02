package com.example.marvel.api

enum class RecordType(open val desc: String? = null) {
    HOURS_WORKED, OVERTIME, ILLNESS, LEAVE,
    HOLIDAY_AVAILABLE, EDUCATION,
    STAND_BY, KILOMETRES,
    /**
     * must have some desc
     */
    OTHER("Placeholder for empty row.");
}
