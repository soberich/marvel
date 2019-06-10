package com.example.marvel.service

/**
 * No-op
 * This just shows a use case where we want to clear up our resource level from business logic, to just call some "service"
 * which just wraps our `program` composition. So, that all what resource layer does is handling tranport on the edge of ther system e.g.
 * - having REST specific annotations, assigning headers, supplying responce statuses, dealing with ViewModels/DTOs for our consumers.
 */
//class EmployeeOrchestrationServiceImpl @Inject constructor(private val VX: Vertx, private val eventBus: EventBus) : EmployeeOrchestrationService {
//
//    val nettyVertxEventLoop: CoroutineDispatcher by lazy { VX.dispatcher() }
//
//    /**
//     * As before, having `[arrow.effects.extensions.io.fx.fx]` here is saying that we target to compose some
//     * computations into a single pure/deferred/delayed/non-subscribed result and execute it later,
//     * OR compose sevral of those and execute them where sequentially or in parallel handling different computation concerns,
//     * like parent or children cancellation by applying different `[kotlinx.coroutines.CoroutineScope]`,
//     * offloading main Netty Vertx dispatcher by submitting different context `[kotlin.coroutines.CoroutineContext]`
//     * using `[kotlinx.coroutines.withContext]` or, as before by applying different context which, however, has other dispatcher
//     * (e.g. by using `[kotlinx.coroutines.CoroutineScope]` with dispatcher other than `[io.vertx.kotlin.coroutines.dispatcher]`)
//     */
//    override suspend fun getEmployeesAsync(): List<Employee> = fx {
//        continueOn(nettyVertxEventLoop)
//
//        CoroutineScope(Dispatchers.IO) {
//            //Flow control is very readable because it is imperative.
//            val fetchEmployeeThenFetchRecords = effect { service.getEmployeesAsync() }.map {
//                it.map { catch(::UnsupportedOperationException) { effect { service.getForPeriodAsync(it.id, Year.now(), Month.JANUARY) } }.flatten() }
//            }
//
//            val januaryRecordsOfEmployeesFetchedInParallel = !parSequence(fetchEmployeeThenFetchRecords.fix().unsafeRunSync())
//
//            januaryRecordsOfEmployeesFetchedInParallel.flatten()
//        }
//    }.unsafeRunSync()
//
//    override suspend fun getForPeriodAsync(employeeId: Long, year: Year, month: Month): List<Record> {
//    }
//}
