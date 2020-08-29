package com.example.marvel.domain.employee

import com.example.marvel.domain.base.SimpleGeneratedIdentityOfLong
import com.example.marvel.domain.recordcollection.RecordCollectionEntity
import javax.persistence.*
import javax.persistence.AccessType.PROPERTY
import javax.persistence.CascadeType.ALL
import kotlin.properties.Delegates

@Entity
@Table(
    indexes = [
        Index(columnList = "email ASC, name DESC")],
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["email"]),
        UniqueConstraint(columnNames = ["name"])])
@Access(PROPERTY)
//@DynamicUpdate
//@SelectBeforeUpdate
//@OptimisticLocking(type = DIRTY)
class EmployeeEntity : SimpleGeneratedIdentityOfLong() {
    //@formatter:off
    @get:
    [Column(nullable = false)]
    var name                                  : String by Delegates.notNull()
    @get:
    [Column(nullable = false)]
    var email                                 : String by Delegates.notNull()

    @get:
    [OrderBy("yearMonth DESC")
    OneToMany(mappedBy = "employee", cascade = [ALL], orphanRemoval = true)]
    var records                               : MutableSet<RecordCollectionEntity> = linkedSetOf()
    //@formatter:on
}

