package com.example.marvel.domain.employee

import com.blazebit.persistence.CriteriaBuilderFactory
import com.blazebit.persistence.view.EntityViewManager
import com.blazebit.persistence.view.EntityViewSetting
import com.example.marvel.api.*
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionUpdateCommand
import com.example.marvel.domain.record.RecordListingView
import com.example.marvel.domain.recordcollection.RecordCollectionEntity
import com.example.marvel.domain.recordcollection.RecordCollectionEntity_
import com.example.marvel.domain.recordcollection.RecordCollectionMapper
import com.example.marvel.spi.EmployeeOperationsServiceNamespace
import com.kumuluz.ee.rest.beans.QueryParameters
import com.kumuluz.ee.rest.utils.JPAUtils
import io.micronaut.spring.tx.annotation.Transactional
import org.springframework.stereotype.Service
import java.time.YearMonth
import java.util.stream.Stream
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.PersistenceUnit

/**
 * This is WIP!
 */
@Named
@Singleton
@Service
@Transactional
class EmployeeBlockingServiceNamespaceImpl @Inject constructor(
//    private val employeeRepository: EmployeeRepository,
    private val empMapper: EmployeeMapper,
    private val recColMapper: RecordCollectionMapper
) : EmployeeOperationsServiceNamespace {

    @set:
    [Inject]
    protected lateinit var em: EntityManager

    @set:
    [Inject]
    protected lateinit var evm: EntityViewManager

    @set:
    [Inject]
    protected var cbf: CriteriaBuilderFactory? = null

    /**
     * Stream should be open on consumer side. Transaction will close it.
     */
    @Transactional(readOnly = true)
    override fun streamEmployees(): Stream<EmployeeView> =
        evm.applySetting(
            EntityViewSetting.create(EmployeeListingView::class.java), /*null*/
            cbf?.create(em, EmployeeEntity::class.java)
        )//FIXME: Only Quarkus currently supports `resultStream` due to reactive transaction propagation.
        .resultList.stream()
        .map(EmployeeView::class.java::cast)

//    //FIXME: Only Quarkus currently supports this.
//    /**
//     *
//     * Stream should be open on consumer side. Transaction will close it.
//     */
//    @Transactional(propagation = SUPPORTS, readOnly = true)
//    override fun streamEmployees(): Stream<EmployeeView> =
//        em.createNamedQuery("Employee.stream", EmployeeListingView::class.java)
//            .resultStream
//            .map(EmployeeView::class.java::cast)

    override fun filterEmployees(filter: String): List<EmployeeView> =
        JPAUtils.queryEntities(em, EmployeeEntity::class.java, QueryParameters.query(filter).build())
            .map {
                evm
                    .createBuilder(EmployeeListingView::class.java)
                    .with(EmployeeListingView_.id, it.id)
                    .with(EmployeeListingView_.version, it.version)
                    .with(EmployeeListingView_.email, it.email)
                    .with(EmployeeListingView_.name, it.name)
                    .build()
            }

    override fun createEmployee(employee: EmployeeCommand.EmployeeCreateCommand): EmployeeDetailedView =
        empMapper.toEntity(employee).also(em::persist).let(empMapper::toCreateView)

    override fun updateEmployee(employee: EmployeeCommand.EmployeeUpdateCommand): EmployeeDetailedView? =
        empMapper.toEntity(employee.id, employee).let(empMapper::toUpdateView)

    override fun listForPeriod(employeeId: Long, yearMonth: YearMonth): List<RecordView> =
        em.createNamedQuery("Record.listForPeriod", RecordListingView::class.java)
            .setParameter(RecordCollectionEntity_.YEAR_MONTH, yearMonth)
            .resultList

    override fun createWholePeriod(records: RecordCollectionCreateCommand): RecordCollectionDetailedView? =
        recColMapper.toCreateView(recColMapper.toEntity(records).also(em::persist))

    override fun updateWholePeriod(records: RecordCollectionUpdateCommand): RecordCollectionDetailedView? =
        em.find(RecordCollectionEntity::class.java, records.id)?.let { recColMapper.toUpdateView(recColMapper.toEntity(records, it).also(em::persist)) }
//
//    fun getAnyUserDemo(): EmployeeDetailedView? =
//        em.createNamedQuery("Employee.detailed", EmployeeDetailedViewDefault::class.java)
//            .setParameter(EmployeeEntity_.ID, Math.random().toLong() % 5)
//            .singleResult

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
