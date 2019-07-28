package com.example.marvel.web.rest.jaxrs

import com.example.marvel.domain.model.api.employee.Employee
import com.example.marvel.domain.model.api.employee.EmployeeCreateCommand
import com.example.marvel.domain.model.api.employee.EmployeeDetailedView
import com.example.marvel.domain.model.api.employee.EmployeeUpdateCommand
import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import com.example.marvel.service.employee.EmployeeOperationsServiceNamespace
import com.example.marvel.web.rest.EmployeeResourceAdapter
import io.reactivex.Flowable
import io.vertx.core.eventbus.EventBus
import io.vertx.kotlin.core.json.jsonObjectOf
import java.time.Month
import java.time.Year
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.inject.Named
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
import javax.ws.rs.core.UriInfo



/**
 * Named imports used for program composition to be more self-documented
 */
@Named
@Path("/")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class EmployeeOrchestrationResource @Inject constructor(/*VX: VertxBare,*/ private val employees: EmployeeOperationsServiceNamespace) : EmployeeResourceAdapter {

    /**
     * Could move to ctor
     */
//    @Inject
//    internal lateinit var vertx: io.vertx.reactivex.core.Vertx

    @set:
    [Inject]
    var eventBus: EventBus? = null

//
//    @Inject
//    internal lateinit var client: io.reactiverse.reactivex.pgclient.PgPool

    @set:
    [Context]
    protected lateinit var headers: HttpHeaders

    @set:
    [Context]
    protected lateinit var uriInfo: UriInfo

    @PostConstruct
    fun init() {
        eventBus?.registerCodec(DomainEventCodec())
    }

    @GET
    @Path("/employee")
    @Produces("application/stream+json")
    override fun getEmployees(): Flowable<Employee> =
            Flowable.fromIterable(Iterable(employees.streamEmployees()::iterator))
                    .doFinally { eventBus?.publish("any.address", jsonObjectOf("pojo event" to """example \"EmployeeCreatedEvent\"""")) }

    @GET
    @Path("/employee/filter")
    override fun filterEmployees(
            @QueryParam("filter") filter: String?): Flowable<Employee> =
            Flowable.fromIterable(employees.filterEmployees(uriInfo.requestUri.query))
                    .doFinally { eventBus?.publish("any.address", jsonObjectOf("pojo event" to """example \"EmployeeCreatedEvent\"""")) }

//    @POST
//    @Path("/employee")
    override fun createEmployee(
            employee: EmployeeCreateCommand): CompletionStage<EmployeeDetailedView> {
        val createEmployee = employees.createEmployee(employee)
        return CompletableFuture.completedFuture(createEmployee)
    }

    @PUT
    @Path("/employee/{id:[1-9][0-9]*}")
    override fun updateEmployee(
            @PathParam("id") id: Long,
            employee: EmployeeUpdateCommand): CompletionStage<EmployeeDetailedView> = CompletableFuture.supplyAsync {
        employees.updateEmployee(id, employee)
    }

    @GET
    @Path("/employee/{id:[1-9][0-9]*}/records")
    override fun getForPeriod(
            @PathParam("id")        id: Long,
            @QueryParam("year")   year: Int,
            @QueryParam("month") month: Month): Flowable<RecordDto> =
            Flowable.fromIterable(Iterable(employees.listForPeriod(id, Year.of(year), month)::iterator))

    @POST
    @Path("/employee/{id:[1-9][0-9]*}/records")
    override fun saveWholePeriod(
            @PathParam("id") id: Long,
            records: RecordCollectionCreateCommand): CompletionStage<RecordCollectionDto> = CompletableFuture.supplyAsync {
        employees.createWholePeriod(id, records) ?: throw NotFoundException()
    }

    @PUT
    @Path("/employee/{id:[1-9][0-9]*}/records")
    override fun adjustWholePeriod(
            @PathParam("id") id: Long,
            records: RecordCollectionUpdateCommand): CompletionStage<RecordCollectionDto> = CompletableFuture.supplyAsync {
        employees.updateWholePeriod(id, records) ?: throw NotFoundException()
    }
}
