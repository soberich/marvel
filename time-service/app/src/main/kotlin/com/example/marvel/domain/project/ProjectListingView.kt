package com.example.marvel.domain.project

import com.blazebit.persistence.view.EntityView
import com.blazebit.persistence.view.IdMapping
import com.example.marvel.api.ProjectView

@EntityView(ProjectEntity::class)
interface ProjectListingView : ProjectView {
    @get:IdMapping
    override val name                : String
}
