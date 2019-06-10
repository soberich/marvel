package com.example.marvel.domain.model.nosql

import com.example.marvel.domain.model.api.employee.Employee
import com.example.marvel.domain.model.api.project.Project
import com.example.marvel.domain.model.api.record.Record
import com.example.marvel.domain.model.api.recordcollection.RecordCollection
import com.example.marvel.domain.model.nosql.base.IdentityOf
import java.time.Month
import java.time.Year

data class RecordCollectionImpl(
    override val id                  : Long,
    override val year                : Year,
    override val month               : Month,
    override val project             : Project,
    override val employee            : Employee
) : IdentityOf<Long>(), RecordCollection {
    override lateinit var records    : List<Record>
}
