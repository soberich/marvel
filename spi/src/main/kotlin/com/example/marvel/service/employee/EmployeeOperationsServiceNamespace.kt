package com.example.marvel.service.employee

import com.example.marvel.domain.model.api.employee.Employee
import com.example.marvel.domain.model.api.employee.EmployeeCreateCommand
import com.example.marvel.domain.model.api.employee.EmployeeDetailedView
import com.example.marvel.domain.model.api.employee.EmployeeUpdateCommand
import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import java.time.Month
import java.time.Year
import java.util.stream.Stream

/**
 * A set of functions arranged in single namespace for convenience and composability.
 * Otherwise we could leave them global.
 * @note Description came from time when we had more functional set-up and suspend functions here.
 */
interface EmployeeOperationsServiceNamespace {

    fun streamEmployees(): Stream<Employee>

    fun filterEmployees(filter: String): List<Employee>

    fun createEmployee(employee: EmployeeCreateCommand): EmployeeDetailedView

    fun updateEmployee(employeeId: Long, employee: EmployeeUpdateCommand): EmployeeDetailedView?

    fun listForPeriod(employeeId: Long, year: Year, month: Month): List<RecordDto>

    fun createWholePeriod(employeeId: Long, records: RecordCollectionCreateCommand): RecordCollectionDto?

    fun updateWholePeriod(employeeId: Long, records: RecordCollectionUpdateCommand): RecordCollectionDto?
}
