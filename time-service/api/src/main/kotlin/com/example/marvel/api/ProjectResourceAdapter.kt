package com.example.marvel.api

import com.example.marvel.api.ProjectCommand.ProjectCreateCommand
import com.example.marvel.api.ProjectCommand.ProjectUpdateCommand
import org.reactivestreams.Publisher
import java.util.concurrent.CompletionStage
import javax.validation.Valid
import javax.validation.constraints.NotNull

interface ProjectResourceAdapter {

    fun getProjects(): Publisher<ProjectView>

    fun createProject(
            @NotNull @Valid project: ProjectCreateCommand): CompletionStage<ProjectDetailedView>

    fun updateProject(
            @NotNull @Valid project: ProjectUpdateCommand): CompletionStage<ProjectDetailedView>
}
