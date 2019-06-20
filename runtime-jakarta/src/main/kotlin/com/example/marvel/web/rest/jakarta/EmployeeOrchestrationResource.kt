package com.example.marvel.web.rest.jakarta

import com.example.marvel.domain.model.api.employee.EmployeeDto
import com.example.marvel.domain.model.api.record.RecordModel
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import com.example.marvel.web.grpc.EmployeeOperationsServiceNamespace
import com.example.marvel.web.rest.EmployeeResourceAdapter
import io.reactivex.Flowable
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.reactivex.core.eventbus.EventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import java.time.Month
import java.time.Year
import java.util.concurrent.CompletionStage
import javax.annotation.PostConstruct
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
    : EmployeeResourceAdapter,
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

    @Context
    internal lateinit var headers: HttpHeaders

    @PostConstruct
    fun init() {
        eventBus.registerCodec(DomainEventCodec())
    }

    @GET
    @Path("/employee")
    override fun getEmployees(): Flowable<EmployeeDto> =
            Flowable.fromIterable(Iterable(employees.streamEmployees()::iterator))
                    .doFinally { eventBus.publish("any.address", JsonObject.mapFrom("""{"pojo event":"example "EmployeeCreatedEvent" "}""")) }

    @GET
    @Path("/employee/{id:[1-9][0-9]*}/records")
    override fun getForPeriod(
            @PathParam("id")        id: Long,
            @QueryParam("year")   year: Int,
            @QueryParam("month") month: Month): Flowable<RecordModel> =
            Flowable.fromIterable(Iterable(employees.listForPeriod(id, Year.of(year), month)::iterator))

    @POST
    @Path("/employee/{id:[1-9][0-9]*}/records")
    override fun saveWholePeriod(
            @PathParam("id") id: Long,
            records: RecordCollectionCreateCommand): CompletionStage<RecordCollectionDto> = CoroutineScope(this).future {
        employees.createWholePeriod(id, records) ?: throw NotFoundException()
    }

    @PUT
    @Path("/employee/{id:[1-9][0-9]*}/records")
    override fun adjustWholePeriod(
            @PathParam("id") id: Long,
            records: RecordCollectionUpdateCommand): CompletionStage<RecordCollectionDto> = CoroutineScope(this).future {
        employees.updateWholePeriod(id, records) ?: throw NotFoundException()
    }
}
