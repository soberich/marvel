@file:Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")

package com.example.marvel.domain.model.api.employee

import arrow.data.ListK
import arrow.optics.optics
import javax.json.bind.annotation.JsonbCreator
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern.Flag.CASE_INSENSITIVE

/**
 * FIXME: Research / File a bug "Local class can not extend sealed class"
 * Unfortunately, there are so many inconveniences with optics, for now I'll leave a compileOnly configuration
 * of `arrow-annotations` so consumer may decide to-arrow-or-not-to-arrow.
 *    Rest in peace, Arrow `@optic`.
 */

interface Employee {
    val id                  : Long
    val name                : String
    val email               : String
}

interface EmployeeDetailedView {
    val id                  : Long
    val name                : String
    val email               : String
}


/**
 * FIXME:  Work around absence of ISO for sealed classes
 * It is clear that those inline properties are useless. They are for demo.
 */
@optics sealed class EmployeeModel {

    companion object {
        inline operator fun invoke(id: Long, name: String, email: String): Employee  =
            object : Employee {
                override inline var id          : Long get() = id
                    set(value) = Unit
                override inline val name        : String get() = name
                override inline val email       : String get() = email
                override inline fun toString()  : String =
                        "You didn't mean to call this ever, do you? Don't call us we'll call you back"
            }
    }
}

@optics data class EmployeeCreateCommand @JsonbCreator constructor(
    @get:
    [NotBlank]
    val name                         : String,
    @get:
    [NotBlank
    Email(flags = [CASE_INSENSITIVE])]
    val email                        : String
) : EmployeeModel() { companion object }

/**
 * Aggregate does't have `ID` in body, but it rather comes from path or param passes for request.
 * This enables us to address on later stages more complex concerns like ABAC.
 */
@optics data class EmployeeUpdateCommand @JsonbCreator constructor(
    @get:
    [NotBlank]
    val name                         : String,
    @get:
    [NotBlank
    Email(flags = [CASE_INSENSITIVE])]
    val email                        : String
) : EmployeeModel() { companion object }

@optics data class EmployeeRequests(val employees: ListK<EmployeeModel>) : List<EmployeeModel> by employees { companion object }
