package com.example.marvel.domain.model.jpa.record

import java.math.BigDecimal
import java.time.LocalDate

interface RecordView {
    val date                : LocalDate
    val type                : RecordType
    val hoursSubmitted      : BigDecimal
    val desc                : String?
    val recordCollectionId  : Long
}
