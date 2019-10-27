package com.example.marvel.domain.record

import com.example.marvel.api.RecordType
import com.example.marvel.api.RecordView
import java.math.BigDecimal
import java.time.LocalDate

data class RecordListingView(
    override val date                : LocalDate,
    override val type                : RecordType,
    override val hoursSubmitted      : BigDecimal,
    override val desc                : String?,
    override val recordCollectionId  : Long
) : RecordView
