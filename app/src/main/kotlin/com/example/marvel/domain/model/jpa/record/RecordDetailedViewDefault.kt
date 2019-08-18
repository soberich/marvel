package com.example.marvel.domain.model.jpa.record

import java.math.BigDecimal
import java.time.LocalDate

data class RecordDetailedViewDefault(
    override val date                : LocalDate,
    override val type                : RecordType,
    override val hoursSubmitted      : BigDecimal,
    override val desc                : String?,
    override val recordCollectionId  : Long
) : RecordDetailedView
