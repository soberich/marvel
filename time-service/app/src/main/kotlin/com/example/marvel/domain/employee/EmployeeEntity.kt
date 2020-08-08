package com.example.marvel.domain.employee

import com.example.marvel.domain.base.SimpleGeneratedIdentityOfLong
import com.example.marvel.domain.recordcollection.RecordCollectionEntity
import io.micronaut.core.annotation.Introspected
import javax.persistence.*
import javax.persistence.AccessType.PROPERTY
import javax.persistence.CascadeType.ALL

@NamedQueries(
    NamedQuery(name = "Employee.stream", query = "SELECT NEW com.example.marvel.domain.employee.EmployeeListingView(e.id, e.email, e.name) FROM EmployeeEntity e"),
    NamedQuery(name = "Employee.detailed", query = "SELECT NEW com.example.marvel.domain.employee.EmployeeDetailedViewDefault(e.id, e.email, e.name) FROM EmployeeEntity e WHERE e.id = :id"))

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
@Introspected
class EmployeeEntity : SimpleGeneratedIdentityOfLong() {
    //@formatter:off
    @get:
    [Column(nullable = false)]
    lateinit var name                                  : String
    @get:
    [Column(nullable = false)]
    lateinit var email                                 : String

    @get:
    [OrderBy("yearMonth DESC")
    OneToMany(mappedBy = "employee", cascade = [ALL], orphanRemoval = true)]
    var records                                        : List<RecordCollectionEntity> = mutableListOf()
    //@formatter:on
}

