package com.example.marvel.domain.employee

//import com.example.marvel.convention.serial.DomainEventCodec
//import io.vertx.core.eventbus.EventBus
//import io.vertx.kotlin.core.json.jsonObjectOf
import com.example.marvel.api.EmployeeCommand.EmployeeCreateCommand
import com.example.marvel.api.EmployeeCommand.EmployeeUpdateCommand
import com.example.marvel.api.EmployeeDetailedView
import com.example.marvel.api.EmployeeResourceAdapter
import com.example.marvel.api.EmployeeView
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionUpdateCommand
import com.example.marvel.api.RecordCollectionDetailedView
import com.example.marvel.convention.utils.RxStreams
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.springframework.http.MediaType
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
//import org.springframework.transaction.TransactionDefinition
//import org.springframework.transaction.annotation.Isolation
//import org.springframework.transaction.annotation.Propagation
//import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
@RestController("/api")
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
@Transactional(propagation = Propagation.REQUIRED, timeout = TransactionDefinition.TIMEOUT_DEFAULT, readOnly = false, isolation = Isolation.DEFAULT)
class EmployeeOrchestrationResource @Inject constructor(/*VX: VertxBare,*/ private val employees: EmployeeBlockingServiceNamespaceImpl) : EmployeeResourceAdapter {

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

    @GetMapping("/employee", produces = ["application/stream+json"])
    override fun getEmployees(): Flowable<EmployeeView> =
        RxStreams.fromCallable(employees::streamEmployees).toFlowable(BackpressureStrategy.MISSING)
//                    .doFinally { eventBus?.publish("any.address", jsonObjectOf("pojo event" to """example \"EmployeeCreatedEvent\"""")) }

//    @GetMapping("/employee/filter")
//    override fun filterEmployees(
//        @RequestParam("filter") filter: String?): Flowable<EmployeeView> =
//            Flowable.fromIterable(employees.filterEmployees(request.queryString))
//                    .doFinally { eventBus?.publish("any.address", jsonObjectOf("pojo event" to """example \"EmployeeCreatedEvent\"""")) }

    @PostMapping("/employee")
    override fun createEmployee(
        @RequestBody employee: EmployeeCreateCommand): CompletionStage<EmployeeDetailedView> {
        val createEmployee = employees.createEmployee(employee)
        return CompletableFuture.completedFuture(createEmployee)
    }

    @PutMapping("/employee")
    override fun updateEmployee(
        @RequestBody employee: EmployeeUpdateCommand): CompletionStage<EmployeeDetailedView> = CompletableFuture.supplyAsync {
        employees.updateEmployee(employee)
    }

//    @GetMapping("/employee/{id:[1-9][0-9]*}/records")
//    override fun getForPeriod(
//        @PathVariable("id") id: Long,
//        @RequestParam("year") year: Int,
//        @RequestParam("month") month: Month): Flowable<RecordView> =
//        Flowable.fromIterable(Iterable(employees.listForPeriod(id, Year.of(year), month)::iterator))

    @PostMapping("/employee/records")
    override fun saveWholePeriod(
        @RequestBody records: RecordCollectionCreateCommand): CompletionStage<RecordCollectionDetailedView> = CompletableFuture.supplyAsync {
        employees.createWholePeriod(records) ?: throw RuntimeException()
    }

    @PutMapping("/employee/records")
    override fun adjustWholePeriod(
        @RequestBody records: RecordCollectionUpdateCommand): CompletionStage<RecordCollectionDetailedView> = CompletableFuture.supplyAsync {
        employees.updateWholePeriod(records) ?: throw RuntimeException()
    }
}
