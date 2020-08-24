package com.example.marvel.api

//import arrow.core.ListK
import java.time.YearMonth
import javax.validation.Valid
import javax.validation.constraints.*

/*@optics*/ sealed class RecordCollectionCommand {
    //@formatter:off
    @get:
    [NotNull
    PastOrPresent]
    abstract val yearMonth           : YearMonth
    @get:
    [NotEmpty]
    abstract val records             : List/*K*/<@Valid RecordCommand>
    //@formatter:on
    companion object

    /*@optics*/ data class RecordCollectionCreateCommand(
        //@formatter:off
        override val yearMonth           : YearMonth,
        @get:
        [NotBlank]
        val projectId                    : String,
        @get:
        [NotNull
        Positive]
        val employeeId                   : Long,
        @get:
        [NotEmpty]
        override val records             : List/*K*/<RecordCommand.RecordCreateCommand>
        //@formatter:on
    ) : RecordCollectionCommand() { companion object }

    /*@optics*/ data class RecordCollectionUpdateCommand(
        //@formatter:off
        @get:
        [NotNull
        Positive]
        val id                           : Long,
        @get:
        [NotNull
        PositiveOrZero]
        val version                      : Int,
        override val yearMonth           : YearMonth,
//        override val projectId           : String,
//        override val employeeId          : Long,
        @get:
        [NotEmpty]
        override val records             : List/*K*/<RecordCommand.RecordUpdateCommand>
        //@formatter:on
    ) : RecordCollectionCommand() { companion object }

    /**
     * no-op
     */
    ///*@optics*/ data class RecordCollectionCommands(val reports: List/*K*/<RecordCollectionCommand>) : List<RecordCollectionCommand> by reports { companion object }
}

