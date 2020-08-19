package com.example.marvel.domain.employee

//import org.springframework.transaction.annotation.Propagation
//import javax.transaction.Transactional
import com.example.marvel.api.EmployeeCommand
import com.example.marvel.api.EmployeeDetailedView
import com.example.marvel.api.EmployeeView
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionUpdateCommand
import com.example.marvel.api.RecordCollectionDetailedView
import com.example.marvel.domain.recordcollection.RecordCollectionEntity
import com.example.marvel.domain.recordcollection.RecordCollectionMapper
import com.example.marvel.spi.EmployeeOperationsServiceNamespace
import com.kumuluz.ee.rest.beans.QueryParameters
import com.kumuluz.ee.rest.utils.JPAUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.TransactionDefinition.TIMEOUT_DEFAULT
import org.springframework.transaction.annotation.Isolation.DEFAULT
import org.springframework.transaction.annotation.Propagation.REQUIRED
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Stream
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * This is WIP!
 */
@Named
@Singleton
@Service
@Transactional(propagation = REQUIRED, timeout = TIMEOUT_DEFAULT, readOnly = false, isolation = DEFAULT)
class EmployeeBlockingServiceNamespaceImpl @Inject constructor(
//    private val employeeRepository: EmployeeRepository,
    private val empMapper: EmployeeMapper,
    private val recColMapper: RecordCollectionMapper
) : EmployeeOperationsServiceNamespace {

    @set:
    [PersistenceContext]
    protected lateinit var em: EntityManager

    /**
     * @implNote Stream should be open on consumer side. Transaction will close it.
     */
//    @Transactional(propagation = NOT_SUPPORTED, readOnly = false)
    override fun streamEmployees(): Stream<EmployeeView> =
        em.createNamedQuery("Employee.stream", EmployeeListingView::class.java)
            .resultStream
            .map(EmployeeView::class.java::cast)

    override fun filterEmployees(filter: String): List<EmployeeView> =
        JPAUtils.queryEntities(em, EmployeeEntity::class.java, QueryParameters.query(filter).build())
            .map { EmployeeListingView(it.id!!, it.name, it.email) }

    override fun createEmployee(employee: EmployeeCommand.EmployeeCreateCommand): EmployeeDetailedView =
        empMapper.toEntity(employee).also(em::persist).let(empMapper::toCreateView)

    override fun updateEmployee(employee: EmployeeCommand.EmployeeUpdateCommand): EmployeeDetailedView? =
        empMapper.toEntity(employee.id, employee).let(empMapper::toUpdateView)

//    override fun listForPeriod(employeeId: Long, year: Year, month: Month): List<RecordView> = employeeRepository.listForPeriod(employeeId, year.value, month)

    override fun createWholePeriod(records: RecordCollectionCreateCommand): RecordCollectionDetailedView? =
        recColMapper.toCreateView(recColMapper.toEntity(records).also(em::persist))

    override fun updateWholePeriod(records: RecordCollectionUpdateCommand): RecordCollectionDetailedView? =
        em.find(RecordCollectionEntity::class.java, records.id)?.let { recColMapper.toUpdateView(recColMapper.toEntity(it.id!!, records).let(em::merge)) }

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
