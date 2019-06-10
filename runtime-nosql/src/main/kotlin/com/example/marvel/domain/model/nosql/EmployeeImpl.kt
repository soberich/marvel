package com.example.marvel.domain.model.nosql

import com.example.marvel.domain.model.api.employee.Employee
import com.example.marvel.domain.model.api.recordcollection.RecordCollection
import com.example.marvel.domain.model.nosql.base.IdentityOf

data class EmployeeImpl(
    override val id                  : Long,
    override val name                : String,
    override val email               : String
) : IdentityOf<Long>(), Employee {
    override lateinit var records    : List<RecordCollection>
}
