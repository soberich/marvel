package com.example.marvel.api

import arrow.core.ListK
import arrow.optics.optics
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.NotNull


@optics sealed class RecordCommand {
    abstract val date                : LocalDate
    abstract val type                : RecordType
    abstract val hoursSubmitted      : BigDecimal
    abstract val desc                : String?
    abstract val recordCollectionId  : Long

    companion object

    @optics data class RecordCreateCommand(
        @get:
        [NotNull]
        override val date                : LocalDate,
        @get:
        [NotNull]
        override val type                : RecordType,
        @get:
        [NotNull]
        override val hoursSubmitted      : BigDecimal,
        override val desc                : String?,
        @get:
        [NotNull]
        override val recordCollectionId  : Long
    ) : RecordCommand() { companion object }

    @optics data class RecordUpdateCommand(
        @get:
        [NotNull]
        val id                           : Long,
        @get:
        [NotNull]
        override val date                : LocalDate,
        @get:
        [NotNull]
        override val type                : RecordType,
        @get:
        [NotNull]
        override val hoursSubmitted      : BigDecimal,
        override val desc                : String?,
        @get:
        [NotNull]
        override val recordCollectionId  : Long
    ) : RecordCommand() { companion object }

    /**
     * no-op
     */
    @optics data class RecordRequests(val records: ListK<RecordCommand>) : List<RecordCommand> by records { companion object }
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
