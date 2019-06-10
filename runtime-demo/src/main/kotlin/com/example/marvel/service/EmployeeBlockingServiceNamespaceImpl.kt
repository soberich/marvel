package com.example.marvel.service

import arrow.effects.fix
import com.example.marvel.domain.model.api.employee.Employee
import com.example.marvel.domain.model.api.employee.EmployeeDto
import com.example.marvel.domain.model.api.employee.toEmployeeDto
import com.example.marvel.domain.model.api.record.Record
import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.api.record.toRecordDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateDto
import com.example.marvel.domain.model.api.recordcollection.toRecordCollectionDto
import com.example.marvel.domain.model.jdbc.query.QEmployeeEntity
import com.example.marvel.domain.model.jdbc.query.QRecordCollectionEntity
import com.example.marvel.domain.model.jpa.recordcollection.toRecordCollectionEntity
import io.ebean.DB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Month
import java.time.Year
import javax.enterprise.inject.Alternative
import kotlin.coroutines.CoroutineContext
import arrow.effects.extensions.io.fx.fx as giveMeConvenientBindingsIWantToComposeConcurrentOps
import kotlinx.coroutines.Dispatchers.IO as suspendBlockingIoTaskAndJumpToWorkerThreadPoolIfNecessary


/**
 * FIXME: Report bug
 *  e: /Projects/marvel/build/tmp/kapt3/stubs/main/com/example/marvel/service/EmployeeBlockingServiceNamespaceImpl.java:69: error: cannot find symbol
 *  @javax.annotation.Nonnull
 *  ^
 *  symbol:   class Nonnull
 *  location: package javax.annotation
 *  e: /Projects/marvel/build/tmp/kapt3/stubs/main/com/example/marvel/service/EmployeeBlockingServiceNamespaceImpl.java:333: error: cannot find symbol
 *  @javax.annotation.Nullable
 *  ^
 *  symbol:   class Nullable
 *  location: package javax.annotation
 *  e: File Object History : []
 *  e: Open Type Names     : []
 *  e: Gen. Src Names      : []
 *  e: Gen. Cls Names      : []
 *  e: Agg. Gen. Src Names : []
 *  e: Agg. Gen. Cls Names : []
 *  e: java.lang.IllegalStateException: Should not be called!
 *  at org.jetbrains.kotlin.types.ErrorUtils$1.getPackage(ErrorUtils.java:86)
 *  at org.jetbrains.kotlin.descriptors.DescriptorUtilKt.resolveClassByFqName(descriptorUtil.kt:21)
 *  at org.jetbrains.kotlin.resolve.checkers.ExperimentalUsageChecker$Companion$checkCompilerArguments$1.invoke(ExperimentalUsageChecker.kt:224)
 *  at org.jetbrains.kotlin.resolve.checkers.ExperimentalUsageChecker$Companion.checkCompilerArguments(ExperimentalUsageChecker.kt:247)
 *  at org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport.analyzeAndReport(AnalyzerWithCompilerReport.kt:108)
 *  at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.analyze(KotlinToJVMBytecodeCompiler.kt:375)
 *  at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.compileModules$cli(KotlinToJVMBytecodeCompiler.kt:123)
 *  at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:131)
 *  at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:54)
 *  at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:84)
 *  at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:42)
 *  at org.jetbrains.kotlin.cli.common.CLITool.exec(CLITool.kt:103)
 */
@Alternative
class EmployeeBlockingServiceNamespaceImpl : /*EmployeeOperationsServiceNamespace,*/ /*Database by DB.getDefault(),*/ CoroutineContext by Dispatchers.IO {

    suspend fun getAnyUserDemo(): EmployeeDto? = withContext(this) {
        QEmployeeEntity().id.eq(Math.random().toLong() % 5).findOne()?.toEmployeeDto()
    }

    suspend fun listEmployees(): List<EmployeeDto> = withContext(this) {
        QEmployeeEntity().findList().map(Employee::toEmployeeDto)
    }

    suspend fun listForPeriod(id: Long, year: Year, month: Month): List<RecordDto> = withContext(this) {
        QRecordCollectionEntity().id.eq(id).month.eq(month).year.eq(year).findOne()?.records?.map(Record::toRecordDto).orEmpty()
    }

    suspend fun createWholePeriod(id: Long, records: RecordCollectionCreateDto): RecordCollectionDto = withContext(this) {
        if (QEmployeeEntity().id.eq(id).exists())
             records.toRecordCollectionEntity().also(DB.getDefault()::insert).toRecordCollectionDto()
        else records.copy(0)
    }

    suspend fun updateWholePeriod(id: Long, records: RecordCollectionUpdateDto): RecordCollectionDto = withContext(this) {
        if (QEmployeeEntity().id.eq(id).exists())
             records.toRecordCollectionEntity().also(DB.getDefault()::update).toRecordCollectionDto()
        else records.copy(0)
    }


    /**
     * D E M O
     * As before, having `[arrow.effects.extensions.io.fx.fx]` here is saying that we target to compose some
     * computations into a single pure/deferred/delayed/non-subscribed result and execute it later,
     * OR compose sevral of those and execute them where sequentially or in parallel handling different computation concerns,
     * like parent or children cancellation by applying different `[kotlinx.coroutines.CoroutineScope]`,
     * offloading main Netty Vertx dispatcher by submitting different context `[kotlin.coroutines.CoroutineContext]`
     * using `[kotlinx.coroutines.withContext]` or, as before by applying different context which, however, has other dispatcher
     * (e.g. by using `[kotlinx.coroutines.CoroutineScope]` with dispatcher other than `[io.vertx.kotlin.coroutines.dispatcher]`)
     */
    object DEMO : CoroutineContext by Dispatchers.IO {
        /**
         * Our function uses blocking IO underneath though it offloads Default eventloop thread
         * AFAIK conventionally we should not make it suspend.
         * but to "possibly" jump to another worker thread from worker thread pool IF we want - we could
         * [kotlinx.coroutines.invoke] (same as [kotlinx.coroutines.withContext])
         * wee need to use `suspend` in order to be able to call aforementioned functions satisfying compiler.
         * Which may seem limiting and, again non-agnostic. We bounded to provider specific calls and restrictions.
         * Arrow imo provides all the same but in more agnostic way having all the same results
         */
        suspend fun getEmployeesDemoCoroutines(): List<Record> = giveMeConvenientBindingsIWantToComposeConcurrentOps {
            continueOn(this@DEMO)
            //Flow control is very readable because it is imperative.
            val fetchEmployeeThenFetchRecords = effect { QEmployeeEntity().findList() }.map {
                it.map {
                    catch(::IllegalStateException) {
                        effect {
                            QRecordCollectionEntity().month.eq(Month.JANUARY).id.eq(it.id).year.eq(Year.now()).findOne()?.records.orEmpty()
                        }
                    }.flatten()
                }
            }
            val januaryRecordsOfEmployeesFetchedInParallel = !this@DEMO.parSequence(fetchEmployeeThenFetchRecords.fix().unsafeRunSync())
            januaryRecordsOfEmployeesFetchedInParallel.flatten()

        }.unsafeRunSync()

        /**
         * exact same as [getEmployeesDemoCoroutines] just polished
         */
        suspend fun getEmployeesDemoArrowPolished(): List<Record> = giveMeConvenientBindingsIWantToComposeConcurrentOps {
            continueOn(this@DEMO)
            val fetchEmployeeThenFetchRecords = effect { QEmployeeEntity().findList() }.map {
                it.map {
                    catch(::IllegalArgumentException) {
                        effect {
                            QRecordCollectionEntity().month.eq(Month.JANUARY).id.eq(it.id).year.eq(Year.now()).findOne()?.records.orEmpty()
                        }
                    }.flatten()
                }
            }.fix().unsafeRunSync()

            (!this@DEMO.parSequence(fetchEmployeeThenFetchRecords)).flatten()

        }.unsafeRunSync()
    }
}



//class EmployeeBlockingServiceNamespaceImpl @Inject constructor(private val VX: Vertx, private val eventBus: EventBus) : EmployeeOrchestrationService {
//
//    val nettyVertxEventLoop: CoroutineDispatcher by lazy { VX.dispatcher() }
//

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
