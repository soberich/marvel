package com.example.marvel.web.rest.jakarta

import com.example.marvel.domain.model.api.employee.EmployeeDto
import com.example.marvel.domain.model.api.employee.EmployeeUpdateCommand
import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import com.example.marvel.domain.model.jpa.employee.EmployeeEntity
import com.example.marvel.domain.model.jpa.employee.toEmployeeDto
import com.example.marvel.domain.model.jpa.recordcollection.toRecordCollection
import com.example.marvel.domain.model.jpa.recordcollection.toRecordCollectionDto
import com.example.marvel.web.grpc.EmployeeOperationsServiceNamespace
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import org.hibernate.Session
import java.time.Month
import java.time.Year
import java.util.stream.Stream
import javax.enterprise.context.ApplicationScoped
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * All calls to (`withContext(this)`)[kotlinx.coroutines.withContext]
 * could be simply convenient overloaded operator (`Dispatchers.IO { ... }`)[kotlinx.coroutines.invoke]
 *
 * FIXME: Caused by: java.lang.NoSuchMethodError: io.quarkus.arc.ArcContainer.isCurrentRequestAsync()Z
 *    at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.handleIfAsyncStarted(TransactionalInterceptorBase.java:114)
 *    at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.invokeInOurTx(TransactionalInterceptorBase.java:105)
 *    at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.doIntercept(TransactionalInterceptorRequired.java:48)
 *    at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.intercept(TransactionalInterceptorBase.java:62)
 *    at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.intercept(TransactionalInterceptorRequired.java:42)
 *    at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired_Bean.intercept(Unknown Source)
 *    at io.quarkus.arc.InvocationContextImpl$InterceptorInvocation.invoke(InvocationContextImpl.java:270)
 *    at io.quarkus.arc.InvocationContextImpl.invokeNext(InvocationContextImpl.java:149)
 *    at io.quarkus.arc.InvocationContextImpl.proceed(InvocationContextImpl.java:173)
 *    at com.example.marvel.web.rest.jakarta.EmployeeBlockingServiceNamespaceImpl_Subclass.listEmployees(Unknown Source)
 *    at com.example.marvel.web.rest.jakarta.EmployeeBlockingServiceNamespaceImpl_ClientProxy.listEmployees(Unknown Source)
 *    at com.example.marvel.web.rest.jakarta.EmployeeOrchestrationResource.getEmployees(EmployeeOrchestrationResource.kt:60)
 *    at com.example.marvel.web.rest.jakarta.EmployeeOrchestrationResource_ClientProxy.getEmployees(Unknown Source)
 *    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
 *    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
 *    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
 *    at java.lang.reflect.Method.invoke(Method.java:498)
 *    at org.jboss.resteasy.core.MethodInjectorImpl.invoke(MethodInjectorImpl.java:151)
 *    ... 71 more
 *    on Quarkus 999-SNAPSHOT by 2019-06-12
 */
//@Transactional
@ApplicationScoped
class EmployeeBlockingServiceNamespaceImpl : EmployeeOperationsServiceNamespace, PanacheRepositoryBase<EmployeeEntity, Long> {

    @PersistenceContext
    lateinit var em: EntityManager

    override fun getAnyUserDemo(): EmployeeDto? =
        findById(Math.random().toLong() % 5)?.toEmployeeDto()

    /**
     * no-op
     * If we'd decide to further supply sort of more functional API, for more rich function compositions
     * in this service for consumers (e.g. Resources/Controllers)
     */
    fun updateEmployeeDemo(employee: EmployeeUpdateCommand): Stream<EmployeeDto> =
            stream("id = ?1", employee.id)
                    .map { EmployeeEntity(employee.copy()) }
                    .map(em::merge)
                    .map(EmployeeEntity::toEmployeeDto)


    fun updateEmployee(employee: EmployeeUpdateCommand): EmployeeDto? =
            findById(employee.id)?.run { EmployeeEntity(employee.copy()) }?.let(em::merge)?.toEmployeeDto()

    override fun listEmployees(): Stream<EmployeeDto> = EmployeeEntity.streamAll().map(EmployeeEntity::toEmployeeDto)

    /**
     * FIXME: Throws `resultStream` MethodNotFound !!??
     * @note Below method works fine.
     */
    fun listForPeriodDemo(id: Long, year: Year, month: Month): Stream<RecordDto> =
            em.createQuery(
            GET_RECORDS_FOR_PERIOD,
            RecordDto::class.java)
            .setParameter(1, id)
            .setParameter(2, month)
            .setParameter(3, year)
            .resultStream

    /**
     * !!Works fine.
     * @note In kotlin 1.3.40 trimming margins, indents, etc. would become intrinsics.
     * No string creation. Multiline for free. (E.g. can be used in annotations)
     * @see [https://youtrack.jetbrains.com/issue/KT-17755]
     * @implNote more an experiment to use JPQL - not an optimal solution.
     */
    override fun listForPeriod(id: Long, year: Year, month: Month): Stream<RecordDto> =
            em.unwrap(Session::class.java)
            .createQuery(
            GET_RECORDS_FOR_PERIOD,
            RecordDto::class.java)
            .setParameter(1, id)
            .setParameter(2, month)
            .setParameter(3, year)
            .stream()

    override fun createWholePeriod(id: Long, records: RecordCollectionCreateCommand): RecordCollectionDto? =
            findById(id)?.run { records.toRecordCollection().also(em::persist).toRecordCollectionDto() }

    override fun updateWholePeriod(id: Long, records: RecordCollectionUpdateCommand): RecordCollectionDto? =
            findById(id)?.run { records.toRecordCollection().let(em::merge).toRecordCollectionDto() }

    companion object {
        private val GET_RECORDS_FOR_PERIOD =
                """SELECT
                      NEW com.example.marvel.domain.model.api.record.RecordCreateCommand(p.date, p.type, p.hoursSubmitted, p.desc, p.report.id)
                   FROM
                      RecordEntity p
                      JOIN
                         p.report c
                   WHERE
                      c.id  = ?1
                      and c.month = ?2
                      and c.year = ?3""".trimIndent()
    }
}
