package com.example.marvel.spi

import com.example.marvel.api.EmployeeCreateCommand
import com.example.marvel.api.EmployeeUpdateCommand
import com.example.marvel.api.EmployeeDetailedView
import com.example.marvel.api.EmployeeView
import com.example.marvel.api.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionUpdateCommand
import com.example.marvel.api.RecordCollectionDetailedView
import com.example.marvel.api.RecordView
import java.time.YearMonth
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

    fun updateEmployee(employee: EmployeeUpdateCommand): EmployeeDetailedView?

    fun listForPeriod(employeeId: Long, yearMonth: YearMonth): List<RecordView>

    fun createWholePeriod(records: RecordCollectionCreateCommand): RecordCollectionDetailedView?

    fun updateWholePeriod(records: RecordCollectionUpdateCommand): RecordCollectionDetailedView?
}
