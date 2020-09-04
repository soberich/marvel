package com.example.marvel.domain.project

import com.example.marvel.api.*
import com.example.marvel.convention.utils.RxStreams
import com.example.marvel.spi.ProjectOperationsServiceNamespace
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


/**
 * Named imports used for program composition to be more self-documented
 */
@Named
@Singleton
@RestController
@RequestMapping("/api", produces = [APPLICATION_JSON_VALUE], consumes = [APPLICATION_JSON_VALUE])
class ProjectOrchestrationResource @Inject constructor(
    private val projects: ProjectOperationsServiceNamespace
) : ProjectResourceAdapter {

    @GetMapping("/project", produces = ["application/stream+json"])
    override fun getProjects(): Flowable<ProjectView> =
        RxStreams.fromCallable(projects::streamProjects).toFlowable(BackpressureStrategy.MISSING)

    @PostMapping("/project")
    override fun createProject(
        @RequestBody project: ProjectCreateCommand
    ): CompletionStage<ProjectDetailedView> = CompletableFuture.supplyAsync {
        projects.createProject(project)
    }

    @PutMapping("/project")
    override fun updateProject(
        @RequestBody project: ProjectUpdateCommand
    ): CompletionStage<ProjectDetailedView> = CompletableFuture.supplyAsync {
        projects.updateProject(project)
    }
}
