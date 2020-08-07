package com.example.marvel.domain.record

import com.example.marvel.api.RecordDetailedView
import com.example.marvel.api.RecordType
import java.time.Duration
import java.time.LocalDate

data class RecordDetailedViewDefault(
    //@formatter:off
    override val date                : LocalDate,
    override val type                : RecordType,
    override val hoursSubmitted      : Duration,
    override val desc                : String?,
    override val recordCollectionId  : Long
    //@formatter:on
) : RecordDetailedView
