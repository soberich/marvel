package com.example.marvel.domain.model.jpa.record

import com.example.marvel.domain.model.api.record.RecordView
import com.example.marvel.domain.model.enums.RecordType
import java.math.BigDecimal
import java.time.LocalDate

data class RecordListingView(
    override val date                : LocalDate,
    override val type                : RecordType,
    override val hoursSubmitted      : BigDecimal,
    override val desc                : String?,
    override val recordCollectionId  : Long
) : RecordView
