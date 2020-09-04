package com.example.marvel.spi

import com.example.marvel.api.ProjectCreateCommand
import com.example.marvel.api.ProjectUpdateCommand
import com.example.marvel.api.ProjectDetailedView
import com.example.marvel.api.ProjectView
import java.util.stream.Stream
import javax.validation.Valid
import javax.validation.constraints.NotNull

interface ProjectOperationsServiceNamespace {

    fun streamProjects(): Stream<ProjectView>

    fun createProject(
            @NotNull @Valid project: ProjectCreateCommand): ProjectDetailedView

    fun updateProject(
            @NotNull @Valid project: ProjectUpdateCommand): ProjectDetailedView?
}
