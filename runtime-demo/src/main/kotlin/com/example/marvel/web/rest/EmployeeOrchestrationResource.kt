package com.example.marvel.web.rest.jakarta

import arrow.core.ForListK
import arrow.core.ListK
import arrow.core.extensions.listk.traverse.traverse
import com.example.marvel.domain.model.api.employee.EmployeeDto
import com.example.marvel.web.grpc.EmployeeOperationsServiceNamespace
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.reactivex.core.eventbus.EventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletionStage
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import kotlin.coroutines.CoroutineContext
import arrow.core.k
import arrow.effects.IO
import arrow.effects.extensions.fx
import arrow.optics.typeclasses.Each
import kotlinx.coroutines.Dispatchers
import javax.servlet.http.HttpServletRequest
import kotlin.streams.toList
import io.vertx.core.Vertx as VertxBare

/**
 * Named imports used for program composition to be more self-documented
 */
@Path("/api")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class EmployeeOrchestrationResource @Inject constructor(VX: VertxBare, private val employees: EmployeeOperationsServiceNamespace):
        /**
         * FIXME: @see in [README.md]
         */
//    EmployeeOperationsServiceNamespace by employees,
    CoroutineContext                   by VX.dispatcher() {

    /**
     * Could move to ctor
     */
    @set:
    [Inject]
    protected lateinit var vertx: io.vertx.reactivex.core.Vertx

    @set:
    [Inject]
    protected lateinit var client: io.reactiverse.reactivex.pgclient.PgPool

    @set:
    [Inject]
    protected var eventBus: EventBus? = null

    @set:
    [Inject]
    protected lateinit var request: HttpServletRequest

    @field:
    [Context]
    protected lateinit var requestHeaders: HttpHeaders


    fun getEmployeesDemo1(): CompletionStage<List<EmployeeDto>> = CoroutineScope(Dispatchers.IO).future {
        val program = IO.fx {
            val all: ListK<EmployeeDto> = !effect { employees.streamEmployees().toList().k() }
            val any: EmployeeDto?        = !effect { employees.getAnyUserDemo() }
            /*
             * Traversing immutable nested structures
             */
//            val updatedEmployees: Employees = Employees.employees.every(ListK.each()).name.modify(Employees(all), String::toUpperCase)
            val updatedEmployees = all.copy(list = listOf())
//            val names: ListK<String>        = Employees.employees.every(ListK.each()).name.getAll(updatedEmployees)
            val names: ListK<String>        = listOf("one", "two", "three").k()
            /*
             * No-op unwrapped alternative
             */
            Each.fromTraverse<ForListK, String>(ListK.traverse()).each().modify(all.map(EmployeeDto::email).k(), String::toUpperCase)

            val namesStr = names.joinToString()
//            requestHeaders.requestHeaders.putSingle("X-ErrorCode", namesStr)
            eventBus?.publish("audit", "employeee.added.$namesStr")
            updatedEmployees
        }

        val execution: Employees = program.unsafeRunSync()
        execution
    }

    fun getEmployeesDemo2(): CompletionStage<List<EmployeeDto>>  = CoroutineScope(this).future {
        emptyList()
//        Nel.fromList(employees.listEmployees()).fold({ headers.requestHeaders.putSingle("X-ErrorCode", "WHAAAT"); emptyList<EmployeeDto>() }) {
//            it.all.k().onEach { EmployeeInput.name.modify(it, String::toUpperCase) }
//        }
    }
}
