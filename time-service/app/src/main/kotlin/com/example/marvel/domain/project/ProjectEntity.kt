package com.example.marvel.domain.project

import javax.persistence.*
import javax.persistence.AccessType.PROPERTY
import kotlin.properties.Delegates

@Entity
@Access(PROPERTY)
//@DynamicUpdate
//@SelectBeforeUpdate
//@OptimisticLocking(type = DIRTY)
class ProjectEntity : GenericTypeMask() {
    //@formatter:off
    @get:
    [Id
    Column(name = "name", length = 50)]
    override var id                           : String by Delegates.notNull()
    //@formatter:on
}

