package com.example.marvel.domain.model.jpa.project

import com.example.marvel.domain.model.jpa.base.IdentityOf
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Access(PROPERTY)
class ProjectEntity : IdentityOf<String>() {
    @get:
    [Id
    Column(name = "name", length = 50)]
    override lateinit var id                  : String
}

