package com.example.marvel.api

import arrow.core.ListK
import java.time.YearMonth
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent

/*@optics*/ sealed class RecordCollectionCommand {
    //@formatter:off
    @get:
    [NotNull
    PastOrPresent]
    abstract val yearMonth           : YearMonth
    @get:
    [NotNull]
    abstract val projectId           : String
    @get:
    [NotNull]
    abstract val employeeId          : Long
    @get:
    [NotEmpty]
    abstract val records             : ListK<@Valid RecordCommand>
    //@formatter:on
    companion object

    /*@optics*/ data class RecordCollectionCreateCommand(
        //@formatter:off
        override val yearMonth           : YearMonth,
        override val projectId           : String,
        override val employeeId          : Long,
        @get:
        [NotEmpty]
        override val records             : ListK<@Valid RecordCommand.RecordCreateCommand>
        //@formatter:on
    ) : RecordCollectionCommand() { companion object }

    /*@optics*/ data class RecordCollectionUpdateCommand(
        //@formatter:off
        @get:
        [NotNull]
        val id                           : Long,
        override val yearMonth           : YearMonth,
        override val projectId           : String,
        override val employeeId          : Long,
        @get:
        [NotEmpty]
        override val records             : ListK<@Valid RecordCommand.RecordUpdateCommand>
        //@formatter:on
    ) : RecordCollectionCommand() { companion object }

    /**
     * no-op
     */
    /*@optics*/ data class RecordCollectionCommands(val reports: ListK<RecordCollectionCommand>) : List<RecordCollectionCommand> by reports { companion object }
}

