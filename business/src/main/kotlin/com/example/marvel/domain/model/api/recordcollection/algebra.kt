@file:Suppress("NOTHING_TO_INLINE")

package com.example.marvel.domain.model.api.recordcollection

import arrow.data.ListK
import arrow.optics.optics
import com.example.marvel.domain.model.api.record.RecordCommand
import com.example.marvel.domain.model.api.record.RecordCreateCommand
import com.example.marvel.domain.model.api.record.RecordDetailedView
import com.example.marvel.domain.model.api.record.RecordUpdateCommand
import java.time.Month
import javax.json.bind.annotation.JsonbCreator
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

interface RecordCollectionView {
    val year                         : Int
    val month                        : Month
    val projectId                    : String
    val employeeId                   : Long
}

interface RecordCollectionDetailedView {
    val year                         : Int
    val month                        : Month
    val projectId                    : String
    val employeeId                   : Long
    val records                      : List<@JvmWildcard RecordDetailedView>
}

@optics sealed class RecordCollectionCommand {
    @get:
    [NotNull]
    abstract val year                : Int
    @get:
    [NotNull]
    abstract val month               : Month
    @get:
    [NotNull]
    abstract val projectId           : String
    @get:
    [NotNull]
    abstract val employeeId          : Long
    @get:
    [NotEmpty]
    abstract val records             : ListK<@Valid RecordCommand>

    companion object
}

@optics data class RecordCollectionCreateCommand @JsonbCreator constructor(
    override val year                : Int,
    override val month               : Month,
    override val projectId           : String,
    override val employeeId          : Long,
    @get:
    [NotEmpty]
    override val records             : ListK<@Valid RecordCreateCommand>
) : RecordCollectionCommand() { companion object }

@optics data class RecordCollectionUpdateCommand @JsonbCreator constructor(
    @get:
    [NotNull]
    val id                           : Long,
    override val year                : Int,
    override val month               : Month,
    override val projectId           : String,
    override val employeeId          : Long,
    @get:
    [NotEmpty]
    override val records             : ListK<@Valid RecordUpdateCommand>
) : RecordCollectionCommand() { companion object }

/**
 * no-op
 */
@optics data class RecordCollectionCommands(val reports: ListK<RecordCollectionCommand>) : List<RecordCollectionCommand> by reports { companion object }

