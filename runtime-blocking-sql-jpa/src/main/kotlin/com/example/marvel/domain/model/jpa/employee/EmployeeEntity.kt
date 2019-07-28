@file:Suppress("NOTHING_TO_INLINE", "DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")

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
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(
        indexes = [
            Index(columnList = "email ASC, name DESC")],
        uniqueConstraints = [
            UniqueConstraint(columnNames = ["email"]),
            UniqueConstraint(columnNames = ["name"])])
@Access(PROPERTY)
data class EmployeeEntity(
    @get:
    [Column(nullable = false)]
    var name                                  : String,
    @get:
    [Column(nullable = false)]
    var email                                 : String
) : SimpleGeneratedIdentityOfLong() {
//    @get:OneToMany(mappedBy = "employee", cascade = [ALL], orphanRemoval = true)
//    lateinit var records                      : List<RecordCollectionEntity>

    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()
}

fun EmployeeCreateCommand.map(mapper: (EmployeeCreateCommand) -> EmployeeCreateView): EmployeeCreateView = mapper(this)
fun EmployeeUpdateCommand.map(id: Long, mapper: (Long, EmployeeUpdateCommand) -> EmployeeUpdateView): EmployeeUpdateView = mapper(id, this)
