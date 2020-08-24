package com.example.marvel.api

//import arrow.core.ListK
import org.hibernate.validator.constraints.time.DurationMax
import org.hibernate.validator.constraints.time.DurationMin
import java.time.Duration
import java.time.LocalDate
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero


/*@optics*/ sealed class RecordCommand {
    //@formatter:off
    @get:
    [NotNull]
    abstract val date                : LocalDate
    @get:
    [NotNull]
    abstract val type                : RecordType
    @get:
    [NotNull
    DurationMax(days = 1L)
    DurationMin(minutes = 10L)]
    abstract val hoursSubmitted      : Duration
    abstract val desc                : String?
    @get:
    [NotNull
    Positive]
    abstract val recordCollectionId  : Long
    //@formatter:on
    companion object

    /*@optics*/ data class RecordCreateCommand(
        //@formatter:off
        override val date                : LocalDate,
        override val type                : RecordType,
        override val hoursSubmitted      : Duration,
        override val desc                : String?,
        override val recordCollectionId  : Long
        //@formatter:on
    ) : RecordCommand() { companion object }

    /*@optics*/ data class RecordUpdateCommand(
        //@formatter:off
        @get:
        [NotNull
        PositiveOrZero]
        val version                      : Int,
        override val date                : LocalDate,
        override val type                : RecordType,
        override val hoursSubmitted      : Duration,
        override val desc                : String?,
        override val recordCollectionId  : Long
        //@formatter:on
    ) : RecordCommand() { companion object }

    /**
     * no-op
     */
    ///*@optics*/ data class RecordRequests(val records: List/*K*/<RecordCommand>) : List<RecordCommand> by records { companion object }
}



///**
// * These functions intentionally left with usage of reflection to see how it would go with Micronaut/Quarkus and native Graal Image
// */
//fun RecordUpdateDtoImpl.toRecordUnsafe() = with(::Record) {
//    val propertiesByName = RecordUpdateDto::class.memberProperties.associateBy { it.name }
//    callBy(parameters.associate {
//        it to propertiesByName[it.name]?.get(this@toRecordUnsafe)
//    })
//}
//
//fun RecordCreateDtoImpl.toRecordUnsafe() = with(::Record) {
//    val propertiesByName = RecordCreateDto::class.memberProperties.associateBy { it.name }
//    callBy(parameters.associate {
//        it to propertiesByName[it.name]?.get(this@toRecordUnsafe)
//    })
//}
