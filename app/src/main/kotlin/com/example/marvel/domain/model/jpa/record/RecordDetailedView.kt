package com.example.marvel.domain.model.jpa.record

import java.math.BigDecimal
import java.time.LocalDate

interface RecordDetailedView {
    val date                : LocalDate
    val type                : RecordType
    val hoursSubmitted      : BigDecimal
    val desc                : String?
    val recordCollectionId  : Long
}
