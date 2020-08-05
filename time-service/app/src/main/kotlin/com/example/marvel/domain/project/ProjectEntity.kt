package com.example.marvel.domain.project

import com.example.marvel.domain.base.AbstractAuditingEntity
import io.micronaut.core.annotation.Introspected
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Access(PROPERTY)
//@DynamicUpdate
//@SelectBeforeUpdate
//@OptimisticLocking(type = DIRTY)
@Introspected
class ProjectEntity : AbstractAuditingEntity<String>() {
    @get:
    [Id
    Column(name = "name", length = 50)]
    override lateinit var id                  : String
}

