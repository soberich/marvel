package com.example.marvel.web.rest

import com.example.marvel.domain.model.api.employee.EmployeeDto
import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import org.reactivestreams.Publisher
import java.time.Month
import java.util.concurrent.CompletionStage
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.QueryParam

/**
 * We use interfaces also for a great Micronaut / Quarkus / RestEasy capability - declarative / proxy client creation.
 * We can later take this interface and just use it, say in tests, instead of additional boilerplate with Restassured.
 */
@Path("/api")
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
    @GET
    @Path("/employee")
    fun getEmployees(): Publisher<EmployeeDto>

    @GET
    @Path("/employee/{id:[1-9][0-9]*}/records")
    fun getForPeriod(
            @PathParam("id")                   id: Long,
            @QueryParam("year")  @NotNull    year: Int,
            @QueryParam("month") @NotNull   month: Month): Publisher<RecordDto>

    @POST
    @Path("/employee/{id:[1-9][0-9]*}/records")
    fun saveWholePeriod(
            @PathParam("id")     id: Long,
            @NotNull @Valid records: RecordCollectionCreateCommand) : CompletionStage<RecordCollectionDto>

    /**
     * !!No creation on PUT verb
     */
    @PUT
    @Path("/employee/{id:[1-9][0-9]*}/records")
    fun adjustWholePeriod(
            @PathParam("id")      id: Long,
            @NotNull @Valid  records: RecordCollectionUpdateCommand) : CompletionStage<RecordCollectionDto>
}
