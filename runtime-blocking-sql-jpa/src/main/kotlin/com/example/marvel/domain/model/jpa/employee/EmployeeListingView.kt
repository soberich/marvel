package com.example.marvel.domain.model.jpa.employee

import com.blazebit.persistence.view.EntityView
import com.blazebit.persistence.view.IdMapping
import com.example.marvel.domain.model.api.employee.Employee

@EntityView(EmployeeEntity::class)
interface EmployeeListingView : Employee {
    @get:
    [IdMapping]
    override val id                           : Long
}
