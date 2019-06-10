package com.example.marvel.web.rest.jakarta

import com.example.marvel.domain.model.api.employee.EmployeeDto
import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import com.example.marvel.web.grpc.EmployeeOperationsServiceNamespace
import com.example.marvel.web.rest.EmployeeResourceAdapter
import io.reactivex.Flowable
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.reactivex.core.eventbus.EventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import java.time.Month
import java.time.Year
import java.util.concurrent.CompletionStage
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.NotFoundException
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import kotlin.coroutines.CoroutineContext
import io.vertx.core.Vertx as VertxBare

/**
 * Named imports used for program composition to be more self-documented
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
class EmployeeOrchestrationResource(VX: VertxBare, private val employees: EmployeeOperationsServiceNamespace) : EmployeeResourceAdapter,
        /**
         * FIXME: @see in [README.md]
         */
//    EmployeeOperationsServiceNamespace by employees,
    CoroutineContext                   by VX.dispatcher() {

    /**
     * Could move to ctor
     */
    @Inject
    internal lateinit var vertx: io.vertx.reactivex.core.Vertx

    @Inject
    internal lateinit var eventBus: EventBus

    @Inject
    internal lateinit var client: io.reactiverse.reactivex.pgclient.PgPool

    @Context
    internal lateinit var headers: HttpHeaders

    override fun getEmployees(): Flowable<EmployeeDto> =
            Flowable.fromIterable(Iterable(employees.listEmployees()::iterator))

    override fun getForPeriod(id: Long, year: Int, month: Month): Flowable<RecordDto> =
            Flowable.fromIterable(Iterable(employees.listForPeriod(id, Year.of(year), month)::iterator))

    override fun saveWholePeriod(id: Long, records: RecordCollectionCreateCommand): CompletionStage<RecordCollectionDto> = CoroutineScope(this).future {
        employees.createWholePeriod(id, records) ?: throw NotFoundException()
    }

    override fun adjustWholePeriod(id: Long, records: RecordCollectionUpdateCommand): CompletionStage<RecordCollectionDto> = CoroutineScope(this).future {
        employees.updateWholePeriod(id, records) ?: throw NotFoundException()
    }

//    fun getEmployeesDemo1(): CompletionStage<List<EmployeeDto>> = CoroutineScope(Dispatchers.IO).future {
//        val program = IO.fx {
//            val all: ListK<EmployeeDto> = !effect { employees.listEmployees().toList().k() }
//            val any: EmployeeDto?        = !effect { employees.getAnyUserDemo() }
//            /*
//             * Traversing immutable nestes structures
//             */
////            val updatedEmployees: Employees = Employees.employees.every(ListK.each()).name.modify(Employees(all), String::toUpperCase)
////            val names: ListK<String>        = Employees.employees.every(ListK.each()).name.getAll(updatedEmployees)
//            /*
//             * No-op unwrapped alternative
//             */
//            Each.fromTraverse<ForListK, String>(ListK.traverse()).each().modify(all.map(EmployeeDto::email).k(), String::toUpperCase)
//
//            val namesStr = names.joinToString()
//            headers.requestHeaders.putSingle("X-ErrorCode", namesStr)
//            eventBus.publish("audit", "employeee.added.$namesStr")
//            updatedEmployees
//        }
//
//        val execution: Employees = program.unsafeRunSync()
//        execution
//    }
//
//    fun getEmployeesDemo2(): CompletionStage<List<EmployeeDto>>  = CoroutineScope(this).future {
//        emptyList()
////        Nel.fromList(employees.listEmployees()).fold({ headers.requestHeaders.putSingle("X-ErrorCode", "WHAAAT"); emptyList<EmployeeDto>() }) {
////            it.all.k().onEach { EmployeeInput.name.modify(it, String::toUpperCase) }
////        }
//    }
}
