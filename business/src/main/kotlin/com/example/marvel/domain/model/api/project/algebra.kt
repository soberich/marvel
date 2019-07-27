@file:Suppress("NOTHING_TO_INLINE")

package com.example.marvel.domain.model.api.project

import arrow.data.ListK
import arrow.optics.optics
import java.time.LocalDateTime
import java.util.UUID
import javax.json.bind.annotation.JsonbCreator
import javax.validation.constraints.NotNull

interface Project {
    val id                  : String
}

/**
 * FIXME: Some ISO for sealed classes =(
 * TODO: Research and/or file a bug.
 */
@optics sealed class ProjectModel : Project {

    companion object {
        /**
         * We don't need these in this POC but it shows the variance of possible implementations
         * TODO: Enrich Project model
         */
        inline operator fun invoke(id: String?)               : ProjectModel =
                ProjectCreateCommand(id ?: "Non-managed project ${UUID.randomUUID()} from ${LocalDateTime.now()}")
        inline operator fun invoke(id: String)                : Project =
                object : Project {
                    override val id                 : String = id
                    override fun toString()         : String     =
                            "You didn't mean to call this ever, do you? Don't call us we'll call you back"
                }
    }
}

@optics data class ProjectDto(
    @PublishedApi
    internal val delegate             : Project
) : Project by delegate { companion object }

@optics data class ProjectCreateCommand @JsonbCreator constructor(
    @get:
    [NotNull]
    override val id                  : String
) : ProjectModel() { companion object }

@optics data class ProjectUpdateCommand @JsonbCreator constructor(
    @get:
    [NotNull]
    override val id                  : String
) : ProjectModel() { companion object }

/**
 * no-op
 */
@optics data class ProjectRequests(val projects: ListK<ProjectModel>) : List<ProjectModel> by projects { companion object }
@optics data class ProjectResponses(val projects: ListK<ProjectDto>) : List<ProjectDto> by projects { companion object }

