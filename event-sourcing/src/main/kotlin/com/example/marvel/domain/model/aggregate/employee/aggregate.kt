package com.example.marvel.domain.model.aggregate.employee

import com.example.marvel.domain.model.jpa.employee.Employee

data class EmployeeAggregate(
    private val delegate    : Employee
) : Employee by delegate {


}

sealed class EmployeeEvent : Employee

data class EmployeeCreated(
    private val delegate    : Employee
) : EmployeeEvent(), Employee by delegate
