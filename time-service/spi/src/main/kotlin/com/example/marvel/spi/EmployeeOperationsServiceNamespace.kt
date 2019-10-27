package com.example.marvel.spi

import com.example.marvel.api.EmployeeCommand.EmployeeCreateCommand
import com.example.marvel.api.EmployeeCommand.EmployeeUpdateCommand
import com.example.marvel.api.EmployeeDetailedView
import com.example.marvel.api.EmployeeView
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionUpdateCommand
import com.example.marvel.api.RecordCollectionDetailedView
import com.example.marvel.api.RecordView
import java.time.Month
import java.time.Year
import java.util.stream.Stream

/**
 * A set of functions arranged in single namespace for convenience and composability.
 * Otherwise we could leave them global.
 * @note Description came from time when we had more functional set-up and suspend functions here.
 */
interface EmployeeOperationsServiceNamespace {

    fun streamEmployees(): Stream<EmployeeView>

    fun filterEmployees(filter: String): List<EmployeeView>

    fun createEmployee(employee: EmployeeCreateCommand): EmployeeDetailedView

    fun updateEmployee(employeeId: Long, employee: EmployeeUpdateCommand): EmployeeDetailedView?

    fun listForPeriod(employeeId: Long, year: Year, month: Month): List<RecordView>

    fun createWholePeriod(employeeId: Long, records: RecordCollectionCreateCommand): RecordCollectionDetailedView?

    fun updateWholePeriod(employeeId: Long, records: RecordCollectionUpdateCommand): RecordCollectionDetailedView?
}
