@file:Suppress("NOTHING_TO_INLINE")

package com.example.marvel.domain.model.api.recordcollection

import arrow.coproduct
import arrow.core.ListK
import arrow.optics.optics
import arrow.product
import com.example.marvel.domain.model.api.record.RecordCreateCommand
import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.api.record.RecordModel
import com.example.marvel.domain.model.api.record.RecordUpdateCommand
import java.time.Month
import javax.json.bind.annotation.JsonbCreator
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Null

interface RecordCollection {
    var id                  : Long?
    val year                : Int
    val month               : Month
}

/**
 * FIXME:  Some ISO for sealed classes =(
 */
@coproduct @optics sealed class RecordCollectionModel : RecordCollection {

    abstract val projectId                    : String
    abstract val employeeId                   : Long
    abstract val records                      : ListK<@Valid RecordModel>

    companion object {
        /**
         * Could use be default args
         */
        inline operator fun invoke(id: Long? = null, year: Int, month: Month, projectId: String, employeeId: Long, records: List<RecordModel> = emptyList()): RecordCollectionDto =
                RecordCollectionDto(RecordCollectionCreateCommand(id ?: 0, year, month, projectId, employeeId, ListK(emptyList())), projectId, employeeId, ListK(emptyList()))
        inline operator fun invoke(year: Int, month: Month, projectId: String, employeeId: Long, records: List<RecordModel> = emptyList()): RecordCollectionDto =
                RecordCollectionModel(null, year, month, projectId, employeeId, records)
    }
}

@product @optics data class RecordCollectionDto(
    @PublishedApi
    internal val delegate             : RecordCollection,
    val projectId                    : String,
    val employeeId                   : Long,
    val records                      : List<RecordDto>
) : RecordCollection by delegate { companion object }

@product @optics data class RecordCollectionCreateCommand @JsonbCreator constructor(
    @get:Null
    override var id                  : Long?,
    @get:NotNull
    override val year                : Int,
    @get:NotNull
    override val month               : Month,
    @get:NotNull
    override val projectId           : String,
    @get:NotNull
    override val employeeId          : Long,
    @get:NotEmpty
    override val records             : ListK<@Valid RecordCreateCommand>
) : RecordCollectionModel() { companion object }

@product @optics data class RecordCollectionUpdateCommand @JsonbCreator constructor(
    @get:NotNull
    override var id                  : Long?,
    @get:NotNull
    override val year                : Int,
    @get:NotNull
    override val month               : Month,
    @get:NotNull
    override val projectId           : String,
    @get:NotNull
    override val employeeId          : Long,
    @get:NotEmpty
    override val records             : ListK<@Valid RecordUpdateCommand>
) : RecordCollectionModel() { companion object }

/**
 * no-op
 */
@product @optics data class RecordCollectionRequests(val reports: ListK<RecordCollectionModel>) : List<RecordCollectionModel> by reports { companion object }
@product @optics data class RecordCollectionResponses(val reports: ListK<RecordCollectionDto>) : List<RecordCollectionDto> by reports { companion object }

