package com.example.marvel.web.grpc

import arrow.core.getOrHandle
import arrow.effects.extensions.io.unsafeRun.runBlocking
import arrow.effects.fix
import arrow.unsafe
import com.example.marvel.domain.model.api.record.Record
import com.example.marvel.service.EmployeeOperations
import io.vertx.axle.core.Vertx
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Mono
import java.time.Month
import java.time.Year
import javax.inject.Inject
import javax.ws.rs.BadRequestException
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.HttpHeaders.CONTENT_ID
import kotlin.coroutines.CoroutineContext
import arrow.effects.extensions.io.fx.fx as composeProgram
import io.vertx.core.Vertx as VertxBare

/**
 * Named imports used for program composition to be more self-documented
 */
class StubResource @Inject constructor(VX: VertxBare, private val service: EmployeeOperations): CoroutineContext by VX.dispatcher() {

    /**
     * Could move to ctor
     */
    @Inject
    internal lateinit var vertx: Vertx

    @Context
    internal lateinit var requestHeaders: HttpHeaders

    /**
     * Submitting the scope here (instead using, say, `[kotlinx.coroutines.GlobalScope]`) means if service (e.g. Netty event loop) dies - we cancel all jobs
     */
     fun exampleInital(): Mono<List<Record>> = CoroutineScope(this).mono {

        /**
         * We want to compose program, to orchestrate it in a convenient way, there is no other superior goal for this bindings.
         */
        //this is a named import of `arrow.effects.extensions.io.fx.fx`
        val program = composeProgram {
            /**
             *  we can switch back and forth by [arrow.effects.typeclasses.Async.continueOn]
             *  or by [kotlinx.coroutines.withContext]
             */
            continueOn(Dispatchers.IO)
            //Flow control is very readable because it is imperative.
            val fetchEmployeeThenFetchRecords = effect { service.getEmployeesAsync() }.map {
                it.map { catch(::BadRequestException) { effect { service.getForPeriodAsync(it.id, Year.now(), Month.JANUARY) } }.flatten() }
            }

            // fire
            val januaryRecordsOfEmployeesFetchedInParallel = !parSequence(fetchEmployeeThenFetchRecords.fix().unsafeRunSync())

            januaryRecordsOfEmployeesFetchedInParallel.flatten()
        }

        /**
         * we can supply headers without polluting return types (e.g. without ResponseEntity<?>)
         */
        program.attempt().unsafeRunSync().fold({
            requestHeaders.requestHeaders.putSingle(CONTENT_ID, "big_trouble_report")
            emptyList<Record>()
        }) {
            requestHeaders.requestHeaders.putSingle(CONTENT_ID, "big_success_report")
            it
        }
    }

    /**
     * Submitting the scope here (instead using, say, `[kotlinx.coroutines.GlobalScope]`) means if service (e.g. Netty event loop) dies - we cancel all jobs
     */
    fun exampleFunctional(): Mono<List<Record>> = CoroutineScope(this).mono {
        unsafe {
            runBlocking {
                composeProgram {
                    continueOn(Dispatchers.IO)
                    effect { service.getEmployeesAsync() }
                            .map {
                                it.map { catch(::BadRequestException) { effect { service.getForPeriodAsync(it.id, Year.now(), Month.JANUARY) } }.flatten() }
                            }.map { parSequence(it) }
                            .flatten()
                            .fix()
                            .attempt()
                            .unsafeRunSync()
                            .fold({
                                requestHeaders.requestHeaders.putSingle(CONTENT_ID, "big_trouble_report")
                                emptyList<List<Record>>()
                            }) {
                                requestHeaders.requestHeaders.putSingle(CONTENT_ID, "big_success_report")
                                it
                            }.flatten()
                }
            }
        }
    }

    /**
     * Asynchronous concurrent (parallel) and business domain logic cleaned up from technology / machinery concerns
     * and with no hassle with using library specific APIs (though most operations have common name, you may notice,
     * that we `map` and `flatMap` over wrapped values abstracting from container specific logic, like Flux APIs).
     * Abstracting over behaviour we get clearer picture of API we need to use, as we know that for to `map` and `flatMap`
     * we need Monad(-ic) behaviour and we choose from a wider variety of fixed data types.
     *
     * N.B. We avoid a situation where we can end uo with a deeply nested structure like
     * Flux<Flux<List<Mono<Person>>>> by using higher-kinded types which provide required behaviour through whole type hierarchy on their
     * fixed instances which allows to operate over the value (e.g. `Person`) and do not nest types.
     */
    fun exampleFinal(): Mono<List<Record>> = CoroutineScope(this).mono {

        val program = composeProgram {
            //Flow control is very readable because it is imperative.
            val fetchEmployeeThenFetchRecords = effect { service.getEmployeesAsync() }.map {
                it.map { catch(::BadRequestException) { effect { service.getForPeriodAsync(it.id, Year.now(), Month.JANUARY) } }.flatten() }
            }

            val januaryRecordsOfEmployeesFetchedInParallel = !parSequence(fetchEmployeeThenFetchRecords.fix().unsafeRunSync())

            januaryRecordsOfEmployeesFetchedInParallel.flatten()
        }

        program.attempt().unsafeRunSync().getOrHandle {
            requestHeaders.requestHeaders.putSingle(CONTENT_ID, "big_trouble_report")
            emptyList()
        }
    }


//    override fun listEmployees(): Mono<List<Employee>> = CoroutineScope(this).mono  {
//
//    }
//
//    override fun listForPeriod(id: Long, year: Year, month: Month): Mono<List<Record>> = CoroutineScope(this).mono  {
//    }
}
