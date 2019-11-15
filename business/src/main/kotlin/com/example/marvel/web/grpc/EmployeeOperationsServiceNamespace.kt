package com.example.marvel.web.grpc

import com.example.marvel.domain.model.api.employee.EmployeeDto
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
 * TODO: This should/could be wrapped with GRPC service by SalesForce (Reactive-GRPS).
 *    Or a more generic and proper solution is to use same interface from REST endpoint, and provide another
 *    runtime with GRPC as exchange instead REST. Presumably SalesForce (Reactive-GRPS) should allow that.
 */
interface EmployeeOperationsServiceNamespace {

    fun getAnyUserDemo(): EmployeeDto

    fun streamEmployees(): Stream<EmployeeDto>

    fun listForPeriod(id: Long, year: Year, month: Month): Stream<RecordDto>

    fun createWholePeriod(id: Long, records: RecordCollectionCreateCommand): RecordCollectionDto?

    fun updateWholePeriod(id: Long, records: RecordCollectionUpdateCommand): RecordCollectionDto?

}
