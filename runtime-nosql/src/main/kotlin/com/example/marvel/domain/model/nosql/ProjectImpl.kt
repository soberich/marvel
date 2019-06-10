package com.example.marvel.domain.model.nosql

import com.example.marvel.domain.model.api.project.Project
import com.example.marvel.domain.model.nosql.base.IdentityOf

data class ProjectImpl(
    override val id                  : String
) : IdentityOf<String>(), Project
