package com.example.marvel.domain.model.enums

enum class RecordType {
    HOURS_WORKED, OVERTIME, ILLNESS, LEAVE,
    HOLIDAY_AVAILABLE, EDUCATION,
    STAND_BY, KILOMETRES,
    /**
     * must have some desc
     */
    OTHER {
        override val desc: String = "Placeholder for empty row."

    };

    open val desc: String? = null
}
