package com.example.marvel.domain.model.jpa.employee

import com.example.marvel.domain.model.api.employee.EmployeeCreateCommand
import com.example.marvel.domain.model.api.employee.EmployeeDto
import com.example.marvel.domain.model.api.employee.EmployeeModel
import com.example.marvel.domain.model.api.employee.EmployeeUpdateCommand
import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import com.example.marvel.domain.model.jpa.record.RecordEntity
import com.example.marvel.domain.model.jpa.record.toRecordDto
import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionEntity_
import com.example.marvel.domain.model.jpa.recordcollection.toRecordCollection
import com.example.marvel.domain.model.jpa.recordcollection.toRecordCollectionDto
import com.example.marvel.service.employee.EmployeeOperationsServiceNamespace
import java.time.Month
import java.time.Year
import java.util.stream.Stream
import javax.enterprise.context.ApplicationScoped
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional


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
 *
 * FIXME: All these `/*EmployeeEntity.*/` is due to https://github.com/quarkusio/quarkus/issues/2196
 */
@Transactional
@ApplicationScoped
class EmployeeBlockingServiceNamespaceImpl : EmployeeOperationsServiceNamespace {

    @PersistenceContext
    lateinit var em: EntityManager

    /**
     * TODO: Into interface.
     */
    fun updateEmployee(employee: EmployeeUpdateCommand): EmployeeDto? =
            em.find(EmployeeEntity::class.java, employee.id)
                    ?.run { EmployeeEntity(employee.copy()) }
                    ?.let(em::merge)
                    ?.toEmployeeDto()

    override fun streamEmployees(): Stream<EmployeeDto> =
            em.createQuery("SELECT e FROM EmployeeEntity e", EmployeeEntity::class.java)
                    .resultStream
                    .map(EmployeeEntity::toEmployeeDto)

    /**
     * @note In kotlin 1.3.40 trimming margins, indents, etc. would become intrinsics.
     * No string creation. Multiline for free. (E.g. can be used in annotations)
     * @see [link](https://youtrack.jetbrains.com/issue/KT-17755)
     * @implNote more an experiment to use JPQL - not an optimal solution.
     */
    override fun listForPeriod(id: Long, year: Year, month: Month): Stream<RecordDto> =
            em.createNamedQuery("Record.findForPeriod", RecordEntity::class.java)
                    .setParameter(RecordCollectionEntity_.ID, id)
                    .setParameter(RecordCollectionEntity_.MONTH, month)
                    .setParameter(RecordCollectionEntity_.YEAR, year)
                    .resultStream
                    .map(RecordEntity::toRecordDto)

    override fun createWholePeriod(id: Long, records: RecordCollectionCreateCommand): RecordCollectionDto? =
            em.find(EmployeeEntity::class.java, id)?.run { records.toRecordCollection(em).also(em::persist).toRecordCollectionDto() }

    override fun updateWholePeriod(id: Long, records: RecordCollectionUpdateCommand): RecordCollectionDto? =
            em.find(EmployeeEntity::class.java, id)?.run { records.toRecordCollection(em).let(em::merge).toRecordCollectionDto() }

    fun getAnyUserDemo(): EmployeeDto? =
            em.find(EmployeeEntity::class.java, Math.random().toLong() % 5)?.toEmployeeDto()

    /**
     * no-op
     * If we'd decide to further supply sort of more functional API, for more rich function compositions
     * in this service for consumers (e.g. Resources/Controllers)
     */
    fun updateEmployeeDemo(employee: EmployeeUpdateCommand): Stream<EmployeeDto> =
            Stream.of(em.find(EmployeeEntity::class.java, employee.id))
                    .map { EmployeeEntity(employee.copy()) }
                    .map(em::merge)
                    .map(EmployeeEntity::toEmployeeDto)

    fun listEmployeesDemo(): List<EmployeeModel> {
        val cb = em.criteriaBuilder
        val cq = cb.createQuery(EmployeeCreateCommand::class.java)
        val root = cq.from(EmployeeEntity::class.java)
        return em.createQuery(
                cq.select(
                        cb.construct(EmployeeCreateCommand::class.java,
                                root[EmployeeEntity_.id],
                                root[EmployeeEntity_.name],
                                root[EmployeeEntity_.email]))
        ).resultList
    }
}
