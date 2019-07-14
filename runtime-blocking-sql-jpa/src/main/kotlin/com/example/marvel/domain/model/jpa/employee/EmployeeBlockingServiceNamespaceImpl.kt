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
import com.kumuluz.ee.rest.beans.QueryParameters
import com.kumuluz.ee.rest.utils.JPAUtils
import java.time.Month
import java.time.Year
import java.util.stream.Stream
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional
import javax.transaction.Transactional.TxType.MANDATORY
import javax.transaction.Transactional.TxType.NOT_SUPPORTED


/**
 * All calls to (`withContext(this)`)[kotlinx.coroutines.withContext]
 * could be simply convenient overloaded operator (`Dispatchers.IO { ... }`)[kotlinx.coroutines.invoke]
 */
@Named
@Transactional
@ApplicationScoped
class EmployeeBlockingServiceNamespaceImpl : EmployeeOperationsServiceNamespace {

    @PersistenceContext
    lateinit var em: EntityManager

    /**
     * @implNote Stream should be open on consumer side. Transaction will close it.
     */
    @Transactional(NOT_SUPPORTED)
    override fun streamEmployees(): Stream<EmployeeDto> =
            em.createQuery("SELECT e FROM EmployeeEntity e", EmployeeEntity::class.java).resultStream.map(EmployeeEntity::toEmployeeDto)

    override fun filterEmployees(filter: String): List<EmployeeDto> =
            JPAUtils.queryEntities(em, EmployeeEntity::class.java, QueryParameters.query(filter).build()).map(EmployeeEntity::toEmployeeDto)

    override fun createEmployee(employee: EmployeeCreateCommand): EmployeeDto =
            employee.toEmployee().also(em::persist).toEmployeeDto()

    override fun updateEmployee(employeeId: Long, employee: EmployeeUpdateCommand): EmployeeDto? =
            em.find(EmployeeEntity::class.java, employeeId)?.run { employee.toEmployee().let(em::merge).toEmployeeDto() }




    /**
     * @note In kotlin 1.3.40 trimming margins, indents, etc. would become intrinsics.
     * No string creation. Multiline for free. (E.g. can be used in annotations)
     * @see [link](https://youtrack.jetbrains.com/issue/KT-17755)
     * @implNote more an experiment to use JPQL - not an optimal solution.
     */
    override fun listForPeriod(employeeId: Long, year: Year, month: Month): List<RecordDto> =
            em.createNamedQuery("Record.findForPeriod", RecordEntity::class.java)
                    .setParameter(RecordCollectionEntity_.ID, employeeId)
                    .setParameter(RecordCollectionEntity_.MONTH, month)
                    .setParameter(RecordCollectionEntity_.YEAR, year)
                    .resultList
                    .map(RecordEntity::toRecordDto)

    override fun createWholePeriod(employeeId: Long, records: RecordCollectionCreateCommand): RecordCollectionDto? =
            em.find(EmployeeEntity::class.java, employeeId)?.run { records.toRecordCollection(em).also(em::persist).toRecordCollectionDto() }

    override fun updateWholePeriod(employeeId: Long, records: RecordCollectionUpdateCommand): RecordCollectionDto? =
            em.find(EmployeeEntity::class.java, employeeId)?.run { records.toRecordCollection(em).let(em::merge).toRecordCollectionDto() }

    fun getAnyUserDemo(): EmployeeDto? =
            em.find(EmployeeEntity::class.java, Math.random().toLong() % 5)?.toEmployeeDto()

    /**
     * no-op
     * If we'd decide to further supply sort of more functional API, for more rich function compositions
     * in this service for consumers (e.g. Resources/Controllers)
     */
    @Transactional(MANDATORY)
    fun updateEmployeeDemo(employeeId: Long, employee: EmployeeUpdateCommand): Stream<EmployeeDto> =
            Stream.of(em.find(EmployeeEntity::class.java, employeeId))
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
