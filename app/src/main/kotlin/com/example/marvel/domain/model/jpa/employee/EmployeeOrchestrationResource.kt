package com.example.marvel.domain.model.jpa.employee

import com.example.marvel.api.EmployeeCommand.EmployeeCreateCommand
import com.example.marvel.api.EmployeeCommand.EmployeeUpdateCommand
import com.example.marvel.api.EmployeeDetailedView
import com.example.marvel.api.EmployeeResourceAdapter
import com.example.marvel.api.EmployeeView
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionUpdateCommand
import com.example.marvel.api.RecordCollectionDetailedView
import com.example.marvel.api.RecordView
import com.example.marvel.convention.serial.DomainEventCodec
import com.example.marvel.spi.EmployeeOperationsServiceNamespace
import io.reactivex.Flowable
import io.vertx.core.eventbus.EventBus
import io.vertx.kotlin.core.json.jsonObjectOf
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.time.Month
import java.time.Year
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType


/**
 * Named imports used for program composition to be more self-documented
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
    [Inject]
    protected lateinit var request: HttpServletRequest

    @PostConstruct
    fun init() {
        eventBus?.registerCodec(DomainEventCodec())
    }

    @GetMapping("/employee", produces = ["application/stream+json"])
    override fun getEmployees(): Flowable<EmployeeView> =
            Flowable.fromIterable(Iterable(employees.streamEmployees()::iterator))
                    .doFinally { eventBus?.publish("any.address", jsonObjectOf("pojo event" to """example \"EmployeeCreatedEvent\"""")) }

    @GET
    @Path("/employee/filter")
    override fun filterEmployees(
        @QueryParam("filter") filter: String?): Flowable<EmployeeView> =
            Flowable.fromIterable(employees.filterEmployees(request.queryString))
                    .doFinally { eventBus?.publish("any.address", jsonObjectOf("pojo event" to """example \"EmployeeCreatedEvent\"""")) }

    @PostMapping("/employee")
    override fun createEmployee(
        @RequestBody employee: EmployeeCreateCommand): CompletionStage<EmployeeDetailedView> {
        val createEmployee = employees.createEmployee(employee)
        return CompletableFuture.completedFuture(createEmployee)
    }

    @PutMapping("/employee/{id:[1-9][0-9]*}")
    override fun updateEmployee(
        @PathVariable("id") id: Long,
        @RequestBody employee: EmployeeUpdateCommand): CompletionStage<EmployeeDetailedView> = CompletableFuture.supplyAsync {
        employees.updateEmployee(id, employee)
    }

    @GetMapping("/employee/{id:[1-9][0-9]*}/records")
    override fun getForPeriod(
        @PathVariable("id") id: Long,
        @RequestParam("year") year: Int,
        @RequestParam("month") month: Month): Flowable<RecordView> =
        Flowable.fromIterable(Iterable(employees.listForPeriod(id, Year.of(year), month)::iterator))

    @PostMapping("/employee/{id:[1-9][0-9]*}/records")
    override fun saveWholePeriod(
        @PathVariable("id") id: Long,
        @RequestBody records: RecordCollectionCreateCommand): CompletionStage<RecordCollectionDetailedView> = CompletableFuture.supplyAsync {
        employees.createWholePeriod(id, records) ?: throw RuntimeException()
    }

    @PutMapping("/employee/{id:[1-9][0-9]*}/records")
    override fun adjustWholePeriod(
        @PathVariable("id") id: Long,
        @RequestBody records: RecordCollectionUpdateCommand): CompletionStage<RecordCollectionDetailedView> = CompletableFuture.supplyAsync {
        employees.updateWholePeriod(id, records) ?: throw RuntimeException()
    }
}
