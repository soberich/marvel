package com.example.marvel.web.rest.jaxrs

import com.example.marvel.domain.model.api.employee.EmployeeCreateCommand
import com.example.marvel.domain.model.api.employee.EmployeeDetailedView
import com.example.marvel.domain.model.api.employee.EmployeeUpdateCommand
import com.example.marvel.domain.model.api.employee.EmployeeView
import com.example.marvel.domain.model.api.record.RecordView
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDetailedView
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import com.example.marvel.service.employee.EmployeeOperationsServiceNamespace
import com.example.marvel.web.rest.EmployeeResourceAdapter
import io.reactivex.Flowable
import io.vertx.core.eventbus.EventBus
import io.vertx.kotlin.core.json.jsonObjectOf
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Month
import java.time.Year
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.servlet.http.HttpServletRequest



/**
 * Named imports used for program composition to be more self-documented
 */
@RestController("/")
@RequestMapping(produces = [APPLICATION_JSON_VALUE], consumes = [APPLICATION_JSON_VALUE])
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

    @GetMapping("/employee/filter")
    override fun filterEmployees(
        @RequestParam("filter") filter: String?): Flowable<EmployeeView> =
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
        employee: EmployeeUpdateCommand): CompletionStage<EmployeeDetailedView> = CompletableFuture.supplyAsync {
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
        records: RecordCollectionCreateCommand): CompletionStage<RecordCollectionDetailedView> = CompletableFuture.supplyAsync {
        employees.createWholePeriod(id, records) ?: throw RuntimeException()
    }

    @PutMapping("/employee/{id:[1-9][0-9]*}/records")
    override fun adjustWholePeriod(
        @PathVariable("id") id: Long,
        records: RecordCollectionUpdateCommand): CompletionStage<RecordCollectionDetailedView> = CompletableFuture.supplyAsync {
        employees.updateWholePeriod(id, records) ?: throw RuntimeException()
    }
}
