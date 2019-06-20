@file:Suppress("NOTHING_TO_INLINE")

package com.example.marvel.domain.model.api.record

import com.example.marvel.domain.model.enums.RecordType
import java.math.BigDecimal
import java.time.LocalDate
import javax.json.bind.annotation.JsonbCreator
import javax.validation.constraints.NotNull

interface Record {
    val date                : LocalDate
    val type                : RecordType
    val hoursSubmitted      : BigDecimal
    val desc                : String?
}

/**
 * FIXME: Some ISO for sealed classes =(
 * TODO: Research and/or file a bug.
 */
/*@optics */sealed class RecordModel : Record {

    abstract val recordCollectionId  : Long

    companion object {
        /**
         * We don't need these in this POC but it shows the variance of possible implementations
         */
        inline operator fun invoke(date: LocalDate, type: RecordType, hoursSubmitted: BigDecimal, recordCollectionId: Long)               : RecordModel =
                RecordCreateCommand(date, type, hoursSubmitted, null, recordCollectionId)
        inline operator fun invoke(date: LocalDate, type: RecordType, hoursSubmitted: BigDecimal, desc: String, recordCollectionId: Long) : RecordModel =
                RecordCreateCommand(date, type, hoursSubmitted, desc, recordCollectionId)
        inline operator fun invoke(date: LocalDate, type: RecordType, hoursSubmitted: BigDecimal, desc: String?, recordCollectionId: Long): Record =
                object : Record {
                    override val date               : LocalDate  = date
                    override val type               : RecordType = type
                    override val hoursSubmitted     : BigDecimal = hoursSubmitted
                    override val desc               : String?    = desc
                    override fun toString()         : String     =
                            "You didn't mean to call this ever, do you? Don't call us we'll call you back"
                }
    }
}

data class RecordDto(
    private val delegate             : Record,
    override val recordCollectionId  : Long
) : RecordModel(), Record by delegate { companion object }

/*@optics */data class RecordCreateCommand @JsonbCreator constructor(
    @get:NotNull
    override val date                : LocalDate,
    @get:NotNull
    override val type                : RecordType,
    @get:NotNull
    override val hoursSubmitted      : BigDecimal,
    override val desc                : String?,
    @get:NotNull
    override val recordCollectionId  : Long
) : RecordModel() { companion object }

/*@optics */data class RecordUpdateCommand(
    @get:NotNull
    override val date                : LocalDate,
    @get:NotNull
    override val type                : RecordType,
    @get:NotNull
    override val hoursSubmitted      : BigDecimal,
    override val desc                : String?,
    @get:NotNull
    override val recordCollectionId  : Long
) : RecordModel() { companion object }

/**
 * no-op
 */
/*@optics */data class Records(val records: List/*K*/<RecordDto>) : List<RecordDto> by records { companion object }


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
