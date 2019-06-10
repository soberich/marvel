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
import javax.transaction.Transactional

/**
 * All calls to (`withContext(this)`)[kotlinx.coroutines.withContext]
 * could be simply convenient overloaded operator (`Dispatchers.IO { ... }`)[kotlinx.coroutines.invoke]
 */

@Transactional
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
            findById(employee.id)
                    ?.run { EmployeeEntity(employee.copy()) }
                    ?.also(em::merge)
                    ?.toEmployeeDto()

    override fun listEmployees(): Stream<EmployeeDto> = EmployeeEntity.streamAll().map(EmployeeEntity::toEmployeeDto)

    /**
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
            findById(id)?.run { records.toRecordCollection().also(em::merge).toRecordCollectionDto() }

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
