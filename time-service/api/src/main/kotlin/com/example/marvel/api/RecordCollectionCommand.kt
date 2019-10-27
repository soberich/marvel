package com.example.marvel.api

import arrow.core.ListK
import arrow.optics.optics
import java.time.Month
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

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


    @optics data class RecordCollectionCreateCommand(
        override val year                : Int,
        override val month               : Month,
        override val projectId           : String,
        override val employeeId          : Long,
        @get:
        [NotEmpty]
        override val records             : ListK<@Valid RecordCommand.RecordCreateCommand>
    ) : RecordCollectionCommand() { companion object }

    @optics data class RecordCollectionUpdateCommand(
        @get:
        [NotNull]
        val id                           : Long,
        override val year                : Int,
        override val month               : Month,
        override val projectId           : String,
        override val employeeId          : Long,
        @get:
        [NotEmpty]
        override val records             : ListK<@Valid RecordCommand.RecordUpdateCommand>
    ) : RecordCollectionCommand() { companion object }

    /**
     * no-op
     */
    @optics data class RecordCollectionCommands(val reports: ListK<RecordCollectionCommand>) : List<RecordCollectionCommand> by reports { companion object }
}

