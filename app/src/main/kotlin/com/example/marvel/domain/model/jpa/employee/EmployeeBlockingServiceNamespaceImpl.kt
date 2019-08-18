package com.example.marvel.domain.model.jpa.employee

import com.example.marvel.domain.model.jpa.record.RecordListingView
import com.example.marvel.domain.model.jpa.record.RecordView
import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionCommand.RecordCollectionCreateCommand
import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionCommand.RecordCollectionUpdateCommand
import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionDetailedView
import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionEntity_
import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionMapper
import com.example.marvel.spi.EmployeeOperationsServiceNamespace
import com.kumuluz.ee.rest.beans.QueryParameters
import com.kumuluz.ee.rest.utils.JPAUtils
import org.springframework.stereotype.Service
import java.time.Month
import java.time.Year
import java.util.stream.Stream
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional
import javax.transaction.Transactional.TxType.NOT_SUPPORTED

/**
 * This is WIP!
 */
@Service
@Transactional
class EmployeeBlockingServiceNamespaceImpl @Inject constructor(
    private val empMapper: EmployeeMapper,
    private val recColMapper: RecordCollectionMapper
) : EmployeeOperationsServiceNamespace {

    @set:
    [PersistenceContext]
    protected lateinit var em: EntityManager

    /**
     * @implNote Stream should be open on consumer side. Transaction will close it.
     */
    @Transactional(NOT_SUPPORTED)
    override fun streamEmployees(): Stream<EmployeeView> =
            em.createNamedQuery("Employee.stream", EmployeeListingView::class.java)
                    .resultStream
                    .map(EmployeeView::class.java::cast)

    override fun filterEmployees(filter: String): List<EmployeeView> =
            JPAUtils.queryEntities(em, EmployeeEntity::class.java, QueryParameters.query(filter).build())
                    .map { EmployeeListingView(it.id!!, it.name, it.email) }

    override fun createEmployee(employee: EmployeeCommand.EmployeeCreateCommand): EmployeeDetailedView =
        empMapper.toEntity(employee).also(em::persist).let(empMapper::toCreateView)

    override fun updateEmployee(employeeId: Long, employee: EmployeeCommand.EmployeeUpdateCommand): EmployeeDetailedView? =
        empMapper.toEntity(employeeId, employee).let(em::merge).let(empMapper::toUpdateView)

    override fun listForPeriod(employeeId: Long, year: Year, month: Month): List<RecordView> =
            em.createNamedQuery("Record.listForPeriod", RecordListingView::class.java)
                    .setParameter(RecordCollectionEntity_.ID, employeeId)
                    .setParameter(RecordCollectionEntity_.MONTH, month)
                    .setParameter(RecordCollectionEntity_.YEAR, year)
                    .resultList

    override fun createWholePeriod(employeeId: Long, records: RecordCollectionCreateCommand): RecordCollectionDetailedView? =
            em.find(EmployeeEntity::class.java, employeeId)?.let { recColMapper.toCreateView(recColMapper.toEntity(it.id, records).also(em::persist)) }

    override fun updateWholePeriod(employeeId: Long, records: RecordCollectionUpdateCommand): RecordCollectionDetailedView? =
            em.find(EmployeeEntity::class.java, employeeId)?.run { recColMapper.toUpdateView(recColMapper.toEntity(records.id, records).let(em::merge)) }

    fun getAnyUserDemo(): EmployeeDetailedView? =
        em.createNamedQuery("Employee.detailed", EmployeeDetailedViewDefault::class.java)
            .setParameter(EmployeeEntity_.ID, Math.random().toLong() % 5)
            .singleResult

//
//    /**
//     * no-op
//     * If we'd decide to further supply sort of more functional API, for more rich function compositions
//     * in this service for consumers (e.g. Resources/Controllers)
//     */
//    @Transactional(MANDATORY)
//    fun updateEmployeeDemo(employeeId: Long, employee: EmployeeUpdateCommand): Stream<EmployeeDetailed> =
//            Stream.of(em.find(EmployeeEntity::class.java, employeeId))
//                    .map { EmployeeEntity(employee.copy()) }
//                    .map(em::merge)
//                    .map(EmployeeEntity::toEmployeeDetailed)
//
//    fun listEmployeesDemo(): List<EmployeeModel> {
//        val cb = em.criteriaBuilder
//        val cq = cb.createQuery(EmployeeCreateCommand::class.java)
//        val root = cq.from(EmployeeEntity::class.java)
//        return em.createQuery(
//                cq.select(
//                        cb.construct(EmployeeCreateCommand::class.java,
//                                root[EmployeeEntity_.id],
//                                root[EmployeeEntity_.name],
//                                root[EmployeeEntity_.email]))
//        ).resultList
//    }
}
