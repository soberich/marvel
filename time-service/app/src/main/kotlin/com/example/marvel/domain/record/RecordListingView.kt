package com.example.marvel.domain.record

import com.example.marvel.api.RecordType
import com.example.marvel.api.RecordView
import io.micronaut.core.annotation.Introspected
import java.time.Duration
import java.time.LocalDate

@Introspected
data class RecordListingView(
    //@formatter:off
    override val date                : LocalDate,
    override val type                : RecordType,
    override val hoursSubmitted      : Duration,
    override val desc                : String?,
    override val recordCollectionId  : Long
    //@formatter:on
) : RecordView
