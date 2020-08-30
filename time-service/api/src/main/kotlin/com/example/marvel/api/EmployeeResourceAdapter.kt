package com.example.marvel.api

import com.example.marvel.api.EmployeeCommand.EmployeeCreateCommand
import com.example.marvel.api.EmployeeCommand.EmployeeUpdateCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionUpdateCommand
import org.reactivestreams.Publisher
import java.time.YearMonth
import java.util.concurrent.CompletionStage
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent

/**
 * We use interfaces also for a great Micronaut / Quarkus / RestEasy capability - declarative / proxy client creation.
 * We can later take this interface and just use it, say in tests, instead of additional boilerplate with Restassured.
 * TODO: This should/could be wrapped with GRPC service by SalesForce (Reactive-GRPC).
 *    Or a more generic and proper solution is to use same interface from REST endpoint, and provide another
 *    runtime with GRPC as exchange instead REST. Presumably SalesForce (Reactive-GRPC) should allow that.
 */
interface EmployeeResourceAdapter {

    /**
     * COLD source. Completely fine to use whatever callback/type to return without blocking.
     * In such case CompletionStage is a perfect solution as it serves most descriptive way, saying that
     *    1. It will NOT stream
     *    2. It is not HOT source as said above
     *    3. It targets to just asynchronously process the job
     *       and does not involve all that machinery which "reactive" gives.
     * ...however, since `CompletableFuture` framework does not provide the capability lazily start computation
     * (which we entered to submit result later to event-loop) it can not be expressed with underlying
     * coroutine in laws of CompletableFuture.
     * So, we might eventually have to fallback to Mono, Flux, Publisher, etc.
     * P.S. BTW, In such case return types from RxJava2 serves slightly better as they give idea of COLD/HOT producer
     * and the semantics are more understandable.
     */
    fun getEmployees(): Publisher<EmployeeView>

//    fun filterEmployees(filter: String?): Publisher<EmployeeView>

    fun createEmployee(
            @NotNull @Valid employee: EmployeeCreateCommand): CompletionStage<EmployeeDetailedView>

    fun updateEmployee(
            @NotNull @Valid employee: EmployeeUpdateCommand): CompletionStage<EmployeeDetailedView>

    fun getForPeriod(
            @NotNull                       id: Long,
            @NotNull @PastOrPresent yearMonth: YearMonth): Publisher<RecordView>

    fun saveWholePeriod(
            @NotNull @Valid records: RecordCollectionCreateCommand) : CompletionStage<RecordCollectionDetailedView>

    /**
     * !!No creation on PUT verb
     */
    fun adjustWholePeriod(
            @NotNull @Valid records: RecordCollectionUpdateCommand) : CompletionStage<RecordCollectionDetailedView>
}
