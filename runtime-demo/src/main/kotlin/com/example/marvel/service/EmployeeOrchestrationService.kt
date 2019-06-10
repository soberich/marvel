package com.example.marvel.service

import com.example.marvel.domain.model.api.employee.Employee
import com.example.marvel.domain.model.api.record.Record
import java.time.Month
import java.time.Year

/**
 * ```kotlin
 *
 * /**
 *  * More Generic variant could look as any combination of generified type parameters like
 *  * @param T Item type
 *  * @param K Id type
 *  * @param S Wrapper for the single results e.g a For*K or a ForId
 *  * @param P Wrapper for the streaming results e.g a For*K or a ForListK
 *  */
 *    interface Repository /*<T, K, S, P>*/ {
 *    suspend fun /*<S, T>*/ storeItem(item: T)      : Kind<S, T>
 *    suspend fun /*<S, T>*/ getItem(itemId: K)      : Kind<S, T>
 *    suspend fun /*<S, T>*/ getAllItems()           : Kind<S, ListK<T>>
 *    suspend fun /*<P, T>*/ getAllItemsStreaming()  : Kind<P, T>
 *
 * ```
 */
interface EmployeeOrchestrationService {
//    suspend fun <S> storeItem(item: Employee): Kind<S, Employee>
//    suspend fun <S> getItem(itemId: Long, FX: Fx<S>)    : Kind<S, Employee>
//    suspend fun <S> getAllItems()            : Kind<S, ListK<Employee>>
//    suspend fun <P> getAllItemsStreaming()   : Kind<P, Employee>


    suspend fun getEmployeesAsync(): List<Employee>
    suspend fun getForPeriodAsync(employeeId: Long, year: Year, month: Month): List<Record>
}
