@file:Suppress("OVERRIDE_BY_INLINE")

package com.example.marvel.domain.model.nosql

import com.example.marvel.domain.model.api.record.Record
import com.example.marvel.domain.model.api.recordcollection.RecordCollection
import com.example.marvel.domain.model.enums.RecordType
import com.example.marvel.domain.model.nosql.base.IdentityOf
import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate

/**
 * This could be a just Tuple3
 * but we push to keep hexagonal: less imports (from Arrow-kt in this layer) => better.
 */

data class RecordImpl(
    override val date                : LocalDate,
    override val type                : RecordType,
    override val hoursSubmitted      : BigDecimal,
    override val desc                : String?,
    override val report              : RecordCollection
) : IdentityOf<RecordImpl.RecordId>(), Record {

    @get:
    [JsonIgnore]
    final override inline val id: RecordImpl.RecordId get() = RecordId(report.id, date, type)

    data class RecordId(val recordCollectionId: Long, val date: LocalDate, val type: RecordType) : Serializable
}


