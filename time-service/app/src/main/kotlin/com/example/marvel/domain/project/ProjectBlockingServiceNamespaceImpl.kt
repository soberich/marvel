package com.example.marvel.domain.project

import com.blazebit.persistence.CriteriaBuilderFactory
import com.blazebit.persistence.view.EntityViewManager
import com.example.marvel.api.*
import com.example.marvel.spi.ProjectOperationsServiceNamespace
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
    [Inject PersistenceContext]
    protected lateinit var em: EntityManager

    @set:
    [Inject]
    protected lateinit var evm: EntityViewManager

    @set:
    [Inject]
    protected lateinit var cbf: CriteriaBuilderFactory

    override fun streamProjects(): Stream<ProjectView> =
        evm.applySetting(
            ProjectListingView_.createSettingInit(), /*null*/
            cbf.create(em, ProjectEntity::class.java)
        )//FIXME: Only Quarkus currently supports `resultStream` due to reactive transaction propagation.
        .resultList.stream()
        .map(ProjectView::class.java::cast)

    override fun createProject(project: ProjectCreateCommand): ProjectDetailedView =
        projectMapper.toEntity(project).also(em::persist).let(projectMapper::toCreateView)

    override fun updateProject(project: ProjectUpdateCommand): ProjectDetailedView? =
        projectMapper.toEntity(project.name, project).let(projectMapper::toUpdateView)
}
