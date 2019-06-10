@file:Suppress("NOTHING_TO_INLINE", "DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")

package com.example.marvel.domain.model.jpa.employee

import com.example.marvel.domain.model.api.employee.Employee
import com.example.marvel.domain.model.api.employee.EmployeeCreateCommand
import com.example.marvel.domain.model.api.employee.EmployeeDto
import com.example.marvel.domain.model.api.employee.EmployeeModel
import com.example.marvel.domain.model.api.employee.EmployeeUpdateCommand
import com.example.marvel.domain.model.jpa.base.SimpleGeneratedIdentityOfLong
import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionEntity
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Transient
import javax.persistence.UniqueConstraint

@Entity
@Table(
        indexes = [
            Index(columnList = "email ASC, name DESC")],
        uniqueConstraints = [
            UniqueConstraint(columnNames = ["email"]),
            UniqueConstraint(columnNames = ["name"])])
data class EmployeeEntity(@Transient private val delegate: Employee) : SimpleGeneratedIdentityOfLong(), Employee by delegate {
    @Column(nullable = false)
    override lateinit var name                : String
    @Column(nullable = false)
    override lateinit var email               : String

    @OneToMany(mappedBy = "employee", cascade = [ALL], orphanRemoval = true)
    lateinit var records             : List<RecordCollectionEntity>

    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()

    /**
     * FIXME: This does not work!
     *    @see https://github.com/quarkusio/quarkus/issues/2196
     *    As a workaround we additionally extend `PanacheRepositoryBase` in our services
     *    @note this is NOT due to it is not managed bean! Any setup won't work from here
     *    e.g. Entities/Repositories wont be enhanced in external jar!
     */
    companion object : PanacheRepositoryBase<EmployeeEntity, Long>
}

/**
 * Just for convenience, to embrace more rich development use-cases.
 * Could contain more complex logic.
 */
inline fun EmployeeEntity.toEmployeeDto(): EmployeeDto = EmployeeDto(copy())

inline fun EmployeeModel.toEmployee(): EmployeeEntity = when (this) {
    is EmployeeDto           -> EmployeeEntity(copy())
    is EmployeeCreateCommand -> EmployeeEntity(copy())
    is EmployeeUpdateCommand -> EmployeeEntity(copy())
}

