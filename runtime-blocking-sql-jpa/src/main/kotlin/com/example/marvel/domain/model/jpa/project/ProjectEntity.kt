package com.example.marvel.domain.model.jpa.project

import com.example.marvel.domain.model.api.project.Project
import com.example.marvel.domain.model.jpa.base.IdentityOf
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Access(PROPERTY)
data class ProjectEntity(
    @get:Id @get:Column(name = "name", length = 50)
    override var id                  : String
) : IdentityOf<String>(), Project {

    /**
     * @see com.example.marvel.domain.model.jpa.base.IdentityOf
     */
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()

    /**
     * FIXME:
     *    @see https://github.com/quarkusio/quarkus/issues/2196
     *    As a workaround we additionally extend `PanacheRepositoryBase` in our services
     *    @note this is NOT due to it is not managed bean! Any setup won't work from here
     *    e.g. Entities/Repositories wont be enhanced in external jar!
     */
}

