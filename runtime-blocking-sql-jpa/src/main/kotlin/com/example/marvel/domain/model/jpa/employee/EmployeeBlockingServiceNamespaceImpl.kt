package com.example.marvel.domain.model.jpa.employee

import com.blazebit.persistence.CriteriaBuilderFactory
import com.blazebit.persistence.view.EntityViewManager
import com.example.marvel.domain.model.api.employee.Employee
import com.example.marvel.domain.model.api.employee.EmployeeCreateCommand
import com.example.marvel.domain.model.api.employee.EmployeeDetailedView
import com.example.marvel.domain.model.api.employee.EmployeeUpdateCommand
import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import com.example.marvel.domain.model.jpa.record.RecordEntity
import com.example.marvel.domain.model.jpa.record.toRecordDto
//import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionEntity_
import com.example.marvel.domain.model.jpa.recordcollection.toRecordCollection
import com.example.marvel.domain.model.jpa.recordcollection.toRecordCollectionDto
import com.example.marvel.service.employee.EmployeeOperationsServiceNamespace
import com.kumuluz.ee.rest.beans.QueryParameters
import com.kumuluz.ee.rest.utils.JPAUtils
import java.time.Month
import java.time.Year
import java.util.stream.Stream
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional
import javax.transaction.Transactional.TxType.NOT_SUPPORTED


/**
 * All calls to (`withContext(this)`)[kotlinx.coroutines.withContext]
 * could be simply convenient overloaded operator (`Dispatchers.IO { ... }`)[kotlinx.coroutines.invoke]
 */
@Named
@Transactional
@ApplicationScoped
class EmployeeBlockingServiceNamespaceImpl @Inject constructor(
        private val mapper: EmployeeMapper,
        private val evm: EntityViewManager,
        private val cbf: CriteriaBuilderFactory
) : EmployeeOperationsServiceNamespace {

    @PersistenceContext
    private lateinit var em: EntityManager

    /**
     * @implNote Stream should be open on consumer side. Transaction will close it.
     */
    @Transactional(NOT_SUPPORTED)
    override fun streamEmployees(): Stream<Employee> =
            em.createQuery("SELECT e FROM EmployeeEntity e", EmployeeEntity::class.java)
                    .resultStream.map { evm.convert(it, EmployeeListingView::class.java) }

    override fun filterEmployees(filter: String): List<Employee> =
            JPAUtils.queryEntities(em, EmployeeEntity::class.java, QueryParameters.query(filter).build())
                    .map { evm.convert(it, EmployeeListingView::class.java) }

    override fun createEmployee(employee: EmployeeCreateCommand): EmployeeDetailedView =
            employee.map(mapper::toEntity).also { evm.update(em, it) }

    override fun updateEmployee(employeeId: Long, employee: EmployeeUpdateCommand): EmployeeDetailedView? =
            employee.map(employeeId, mapper::toEntity).also { evm.update(em, it) }

    /**
     * @note In kotlin 1.3.40 trimming margins, indents, etc. would become intrinsics.
     * No string creation. Multiline for free. (E.g. can be used in annotations)
     * @see [link](https://youtrack.jetbrains.com/issue/KT-17755)
     * @implNote more an experiment to use JPQL - not an optimal solution.
     */
    override fun listForPeriod(employeeId: Long, year: Year, month: Month): List<RecordDto> =
            em.createNamedQuery("Record.findForPeriod", RecordEntity::class.java)
//                    .setParameter(RecordCollectionEntity_.ID, employeeId)
//                    .setParameter(RecordCollectionEntity_.MONTH, month)
//                    .setParameter(RecordCollectionEntity_.YEAR, year)
                    .resultList
                    .map(RecordEntity::toRecordDto)

    override fun createWholePeriod(employeeId: Long, records: RecordCollectionCreateCommand): RecordCollectionDto? =
            em.find(EmployeeEntity::class.java, employeeId)?.run { records.toRecordCollection(em).also(em::persist).toRecordCollectionDto() }

    override fun updateWholePeriod(employeeId: Long, records: RecordCollectionUpdateCommand): RecordCollectionDto? =
            em.find(EmployeeEntity::class.java, employeeId)?.run { records.toRecordCollection(em).let(em::merge).toRecordCollectionDto() }

//    fun getAnyUserDemo(): Employee? =
//            em.find(EmployeeEntity::class.java, Math.random().toLong() % 5)?.toEmployeeDto()
//
//    /**
//     * no-op
//     * If we'd decide to further supply sort of more functional API, for more rich function compositions
//     * in this service for consumers (e.g. Resources/Controllers)
//     */
//    @Transactional(MANDATORY)
//    fun updateEmployeeDemo(employeeId: Long, employee: EmployeeUpdateCommand): Stream<EmployeeDto> =
//            Stream.of(em.find(EmployeeEntity::class.java, employeeId))
//                    .map { EmployeeEntity(employee.copy()) }
//                    .map(em::merge)
//                    .map(EmployeeEntity::toEmployeeDto)
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
