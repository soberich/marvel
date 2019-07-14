@file:Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")

package com.example.marvel.domain.model.api.employee

import arrow.data.ListK
import arrow.optics.optics
import javax.json.bind.annotation.JsonbCreator
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Null
import javax.validation.constraints.Pattern.Flag.CASE_INSENSITIVE

/**
 * FIXME: Research / File a bug "Local class can not extend sealed class"
 * Unfortunately, there are so many inconveniences with optics, for now I'll leave a compileOnly configuration
 * of `arrow-annotations` so consumer may decide to-arrow-or-not-to-arrow.
 *    Rest in peace, Arrow `@optic`.
 */

interface Employee {
    var id                  : Long?
    val name                : String
    val email               : String
}

/**
 * FIXME:  Work around absence of ISO for sealed classes
 * It is clear that those inline properties are useless. They are for demo.
 */
@optics sealed class EmployeeModel : Employee {

    companion object {
        inline operator fun invoke(           name: String, email: String): EmployeeModel = EmployeeCreateCommand(null, name, email)
        inline operator fun invoke(id: Long,  name: String, email: String): EmployeeModel = EmployeeCreateCommand(id, name, email)
        inline operator fun invoke(id: Long?, name: String, email: String): Employee  =
            object : Employee {
                override inline var id          : Long? get() = id
                    set(value) = Unit
                override inline val name        : String get() = name
                override inline val email       : String get() = email
                override inline fun toString()  : String =
                        "You didn't mean to call this ever, do you? Don't call us we'll call you back"
            }
    }
}

/**
 * FIXME: Track https://github.com/arrow-kt/arrow/issues/1494
 */
@optics data class EmployeeDto(
    @PublishedApi
    internal val delegate: Employee
) : Employee by delegate { companion object }

@optics data class EmployeeCreateCommand @JsonbCreator constructor(
    @get:Null
    override var id                  : Long?,
    @get:NotBlank
    override val name                : String,
    @get:NotBlank
    @get:Email(flags = [CASE_INSENSITIVE])
    override val email               : String
) : EmployeeModel() { companion object }

@optics data class EmployeeUpdateCommand @JsonbCreator constructor(
    @get:NotNull
    override var id                  : Long?,
    @get:NotBlank
    override val name                : String,
    @get:NotBlank
    @get:Email(flags = [CASE_INSENSITIVE])
    override val email               : String
) : EmployeeModel() { companion object }

@optics data class EmployeeRequests(val employees: ListK<EmployeeModel>) : List<EmployeeModel> by employees { companion object }
@optics data class EmployeeResponses(val employees: ListK<EmployeeDto>) : List<EmployeeDto> by employees { companion object }
