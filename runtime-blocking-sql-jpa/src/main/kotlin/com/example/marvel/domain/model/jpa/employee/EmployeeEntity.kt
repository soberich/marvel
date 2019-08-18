@file:Suppress("NOTHING_TO_INLINE")

package com.example.marvel.domain.model.jpa.employee

import com.example.marvel.domain.model.api.employee.EmployeeCreateCommand
import com.example.marvel.domain.model.api.employee.EmployeeUpdateCommand
import com.example.marvel.domain.model.jpa.base.SimpleGeneratedIdentityOfLong
import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionEntity
import org.mapstruct.factory.Mappers
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.UniqueConstraint


@NamedQueries(
    NamedQuery(name = "Employee.stream", query = "SELECT NEW com.example.marvel.domain.model.jpa.employee.EmployeeListingView(e.id, e.email, e.name) FROM EmployeeEntity e"),
    NamedQuery(name = "Employee.detailed", query = "SELECT NEW com.example.marvel.domain.model.jpa.employee.EmployeeDetailedViewImpl(e.id, e.email, e.name) FROM EmployeeEntity e WHERE e.id = :id")
)

@Entity
@Table(
        indexes = [
            Index(columnList = "email ASC, name DESC")],
        uniqueConstraints = [
            UniqueConstraint(columnNames = ["email"]),
            UniqueConstraint(columnNames = ["name"])])
@Access(PROPERTY)
class EmployeeEntity : SimpleGeneratedIdentityOfLong() {
    @get:
    [Column(nullable = false)]
    lateinit var name                                  : String
    @get:
    [Column(nullable = false)]
    lateinit var email                                 : String

    @get:
    [OneToMany(mappedBy = "employee", cascade = [ALL], orphanRemoval = true)]
    lateinit var records                      : List<RecordCollectionEntity>
}

