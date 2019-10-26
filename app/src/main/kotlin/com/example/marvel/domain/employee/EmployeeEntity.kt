package com.example.marvel.domain.employee

import com.example.marvel.domain.base.SimpleGeneratedIdentityOfLong
import com.example.marvel.domain.recordcollection.RecordCollectionEntity
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
    NamedQuery(name = "Employee.stream", query = "SELECT NEW com.example.marvel.domain.employee.EmployeeListingView(e.id, e.email, e.name) FROM EmployeeEntity e"),
    NamedQuery(name = "Employee.detailed", query = "SELECT NEW com.example.marvel.domain.employee.EmployeeDetailedViewDefault(e.id, e.email, e.name) FROM EmployeeEntity e WHERE e.id = :id")
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

