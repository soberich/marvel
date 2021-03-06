package com.example.marvel.domain.employee

//import com.example.marvel.convention.serial.DomainEventCodec
//import io.vertx.core.eventbus.EventBus
//import io.vertx.kotlin.core.json.jsonObjectOf
import com.example.marvel.api.*
import com.example.marvel.api.EmployeeCreateCommand
import com.example.marvel.api.EmployeeUpdateCommand
import com.example.marvel.api.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionUpdateCommand
import com.example.marvel.convention.utils.RxStreams
import com.example.marvel.spi.EmployeeOperationsServiceNamespace
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*
import java.time.YearMonth
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


/**
 * Named imports used for program composition to be more self-documented
 */
@Named
@Singleton
@RestController
@RequestMapping(value = ["/api"], produces = [APPLICATION_JSON_VALUE], consumes = [APPLICATION_JSON_VALUE])
class EmployeeOrchestrationResource(/*VX: VertxBare,*/) :
    EmployeeResourceAdapter {

    @set:
    [Inject]
    protected lateinit var employees: EmployeeOperationsServiceNamespace
    /**
     * Could move to ctor
     */
//    @Inject
//    internal lateinit var vertx: io.vertx.reactivex.core.Vertx

//    @set:
//    [Inject]
//    var eventBus: EventBus? = null

//
//    @Inject
//    internal lateinit var client: io.reactiverse.reactivex.pgclient.PgPool

//    @set:
//    [Inject]
//    protected lateinit var request: HttpServletRequest

//    @PostConstruct
//    fun init() {
//        eventBus?.registerCodec(DomainEventCodec())
//    }

//    @Transactional(propagation = NOT_SUPPORTED, readOnly = true)
    @GetMapping(value = ["/employee"], produces = ["application/stream+json"])
    override fun getEmployees(): Flowable<EmployeeView> =
        RxStreams.fromCallable(employees::streamEmployees).toFlowable(BackpressureStrategy.MISSING)
//                    .doFinally { eventBus?.publish("any.address", jsonObjectOf("pojo event" to """example \"EmployeeCreatedEvent\"""")) }

//    @GetMapping("/employee/filter")
//    override fun filterEmployees(
//        @RequestParam("filter") filter: String?): Flowable<EmployeeView> =
//            Flowable.fromIterable(employees.filterEmployees(request.queryString))
//                    .doFinally { eventBus?.publish("any.address", jsonObjectOf("pojo event" to """example \"EmployeeCreatedEvent\"""")) }

    @PostMapping(value = ["/employee"])
    override fun createEmployee(
        @RequestBody employee: EmployeeCreateCommand
    ): CompletionStage<EmployeeDetailedView> {
        val createEmployee = employees.createEmployee(employee)
        return CompletableFuture.completedFuture(createEmployee)
    }

    @PutMapping(value = ["/employee"])
    override fun updateEmployee(
        @RequestBody employee: EmployeeUpdateCommand
    ): CompletionStage<EmployeeDetailedView> = CompletableFuture.supplyAsync {
        employees.updateEmployee(employee)
    }

    @GetMapping(value = ["/employee/{id:[1-9][0-9]*}/records"])
    override fun getForPeriod(
        @PathVariable("id") id: Long,
        @RequestParam("yearMonth") yearMonth: YearMonth
    ): Flowable<RecordView> =
        Flowable.fromIterable(
            Iterable(
                employees.listForPeriod(
                    id,
                    yearMonth
                )::iterator
            )
        ) //TODO: remove SAM helper on Kotlin 1.4+

    @PostMapping(value = ["/employee/records"])
    override fun saveWholePeriod(
        @RequestBody records: RecordCollectionCreateCommand
    ): CompletionStage<RecordCollectionDetailedView> = CompletableFuture.supplyAsync {
        employees.createWholePeriod(records) ?: throw RuntimeException("Input is incorrect!")
    }

    @PutMapping(value = ["/employee/records"])
    override fun adjustWholePeriod(
        @RequestBody records: RecordCollectionUpdateCommand
    ): CompletionStage<RecordCollectionDetailedView> = CompletableFuture.supplyAsync {
        employees.updateWholePeriod(records) ?: throw RuntimeException("Report with id ${records.id} was not found!")
    }
}
