package com.example.marvel.api

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern.Flag.CASE_INSENSITIVE
import javax.validation.constraints.Positive

/**
 * Thid aggregate does't have `ID` in body, but it rather comes from path or param passes for request as essential param.
 * This enables us to address on later stages more complex concerns like ABAC, as we don't need to deser before dispatching request.
 */
@Introspected
/*@optics*/ sealed class EmployeeCommand {
    @get:
    [NotBlank]
    abstract val name                : String
    @get:
    [NotBlank
    Email(flags = [CASE_INSENSITIVE])]
    abstract val email               : String

    companion object

    @Introspected
    /*@optics*/ data class EmployeeCreateCommand(
        override val name                : String,
        override val email               : String
    ) : EmployeeCommand() { companion object }

    @Introspected
    /*@optics*/ data class EmployeeUpdateCommand(
        @get:
        [NotNull
        Positive]
        val id                           : Long,
        override val name                : String,
        override val email               : String
    ) : EmployeeCommand() { companion object }
}
