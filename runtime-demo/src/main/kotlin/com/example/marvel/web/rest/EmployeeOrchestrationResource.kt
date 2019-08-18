package com.example.marvel.web.rest.jakarta

import com.example.marvel.domain.model.api.employee.EmployeeDto
import com.example.marvel.domain.model.api.record.RecordCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import com.example.marvel.service.EmployeeOperationsServiceNamespace
import com.example.marvel.web.rest.EmployeeResourceAdapter
import io.reactivex.Flowable
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.reactivex.core.eventbus.EventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import java.time.Month
import java.time.Year
import java.util.concurrent.CompletionStage
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.NotFoundException
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import kotlin.coroutines.CoroutineContext
import io.vertx.core.Vertx as VertxBare

/**
 * Named imports used for program composition to be more self-documented
 */
@Path("/api")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class EmployeeOrchestrationResource @Inject constructor(VX: VertxBare, private val employees: EmployeeOperationsServiceNamespace)
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
