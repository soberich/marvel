package com.example.marvel.domain.project

import com.example.marvel.api.ProjectView

data class ProjectListingView(
    //@formatter:off
    override val name                : String,
    override val version             : Int
    //@formatter:on
) : ProjectView
