package com.example.marvel.domain.model.jdbc

import com.example.marvel.domain.model.api.project.Project
import com.example.marvel.domain.model.jdbc.base.IdentityOf
import io.ebean.Finder
import io.ebean.annotation.Cache
import io.ebean.annotation.History
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Cache
@History
data class ProjectEntity(
    @Id
    @Column(name = "name", length = 50)
    override val id                  : String
) : IdentityOf<String>(), Project {

    /**
     * @see com.example.marvel.domain.model.jdbc.base.IdentityOf
     */
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()

    companion object : Finder<String, ProjectEntity>(ProjectEntity::class.java)
}
