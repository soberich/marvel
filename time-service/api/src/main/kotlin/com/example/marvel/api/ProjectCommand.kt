package com.example.marvel.api

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

/**
 * This aggregate doesn't have `ID` in body, but it rather comes from path or param passes for request as essential param.
 * This enables us to address on later stages more complex concerns like ABAC, as we don't need to deser before dispatching request.
 */
/*@optics*/ sealed class ProjectCommand {
    //@formatter:off
    @get:
    [NotEmpty]
    abstract val name                    : String
    //@formatter:on
    companion object

}

/*@optics*/ data class ProjectCreateCommand(
    //@formatter:off
    override val name                : String
    //@formatter:on
) : ProjectCommand() { companion object }

/*@optics*/ data class ProjectUpdateCommand(
    //@formatter:off
    override val name                : String,
    @get:
    [NotNull
    PositiveOrZero]
    val version                      : Int
    //@formatter:on
) : ProjectCommand() { companion object }
