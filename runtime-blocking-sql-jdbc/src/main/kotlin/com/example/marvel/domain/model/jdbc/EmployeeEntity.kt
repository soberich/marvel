package com.example.marvel.domain.model.jdbc

import com.example.marvel.domain.model.api.employee.Employee
import com.example.marvel.domain.model.jdbc.base.IdentityOf
import io.ebean.Finder
import io.ebean.annotation.Cache
import io.ebean.annotation.History
import javax.persistence.*
import javax.persistence.CascadeType.ALL

@Entity
@Cache
@History
data class EmployeeEntity(
    @Id
    @GeneratedValue
    @Column(updatable = false)
    override val id                  : Long,
    @Column(nullable = false)
    override val name                : String,
    @Column(nullable = false, unique = true)
    override val email               : String
) : IdentityOf<Long>(), Employee {

    @OneToMany(cascade = [ALL], orphanRemoval = true)
    override lateinit var records: List<RecordCollectionEntity>

    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()

    companion object : Finder<Long, EmployeeEntity>(EmployeeEntity::class.java)
}
