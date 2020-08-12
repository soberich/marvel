package com.example.marvel.domain.project

import com.example.marvel.domain.base.AbstractAuditingEntity
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import kotlin.properties.Delegates

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

