package com.example.marvel.domain.project

import com.example.marvel.domain.base.AbstractAuditingEntity
import javax.persistence.*
import javax.persistence.AccessType.PROPERTY
import kotlin.properties.Delegates

@NamedQueries(
    NamedQuery(name = "Project.stream", query = "SELECT NEW ProjectListingView(e.id, e.version) FROM ProjectEntity e"))

@Entity
@Access(PROPERTY)
//@DynamicUpdate
//@SelectBeforeUpdate
//@OptimisticLocking(type = DIRTY)
class ProjectEntity : AbstractAuditingEntity<String>() {
    //@formatter:off
    @get:
    [Id
    Column(name = "name", length = 50)]
    override var id                           : String by Delegates.notNull()
    //@formatter:on
}

