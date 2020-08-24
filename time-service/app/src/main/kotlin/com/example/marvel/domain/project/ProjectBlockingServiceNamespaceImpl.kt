package com.example.marvel.domain.project

import com.example.marvel.api.ProjectCommand
import com.example.marvel.api.ProjectDetailedView
import com.example.marvel.api.ProjectView
import com.example.marvel.spi.ProjectOperationsServiceNamespace
import io.micronaut.spring.tx.annotation.Transactional
import org.springframework.stereotype.Service
import java.util.stream.Stream
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Named
@Singleton
@Service
@Transactional
class ProjectBlockingServiceNamespaceImpl @Inject constructor(
    private val projectMapper: ProjectMapper
) : ProjectOperationsServiceNamespace {

    @set:
    [PersistenceContext]
    protected lateinit var em: EntityManager

    override fun streamProjects(): Stream<ProjectView> =
        em.createNamedQuery("Project.stream", ProjectListingView::class.java)
            //FIXME: Only Quarkus currently supports `resultStream` due to reactive transaction propagation.
            .resultList.stream()
            .map(ProjectView::class.java::cast)

    override fun createProject(project: ProjectCommand.ProjectCreateCommand): ProjectDetailedView =
        projectMapper.toEntity(project).also(em::persist).let(projectMapper::toCreateView)

    override fun updateProject(project: ProjectCommand.ProjectUpdateCommand): ProjectDetailedView? =
        projectMapper.toEntity(project.name, project).let(projectMapper::toUpdateView)
}