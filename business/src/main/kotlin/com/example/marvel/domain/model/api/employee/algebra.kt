@file:Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")

package com.example.marvel.domain.model.api.employee

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Null
import javax.validation.constraints.Pattern.Flag.CASE_INSENSITIVE

/**
 * FIXME: Research / File a bug "Local class can not extend sealed class"
 * FIXME: Unfortunately, there are so many inconveniences with optics, that for now I give up on them.
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
/*@optics */sealed class EmployeeModel : Employee {

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

data class EmployeeDto(
    private val delegate: Employee
) : EmployeeModel(), Employee by delegate { companion object }

/*@optics */data class EmployeeCreateCommand(
    @get:Null
    override var id                  : Long?,
    @get:NotBlank
    override val name                : String,
    @get:NotBlank
    @get:Email(flags = [CASE_INSENSITIVE])
    override val email               : String
) : EmployeeModel() { companion object }

/*@optics */data class EmployeeUpdateCommand(
    @get:NotNull
    override var id                  : Long?,
    @get:NotBlank
    override val name                : String,
    @get:NotBlank
    @get:Email(flags = [CASE_INSENSITIVE])
    override val email               : String
) : EmployeeModel() { companion object }

/*@optics */data class Employees(val employees: List/*K*/<EmployeeDto>) : List<EmployeeDto> by employees { companion object }
