package org.openapitools.client.api

import java.io.File

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.core.structure.PopulationBuilder
import io.gatling.http.Predef._

import scala.collection.mutable

class DefaultApiSimulation extends Simulation {

    private def getCurrentDirectory = new File("").getAbsolutePath
    private def userDataDirectory = getCurrentDirectory + "/src/main/resources/data"

    // basic test setup
    private val configName = System.getProperty("./conf/testConfig", "./conf/baseline")
    private val config = ConfigFactory.load(configName).withFallback(ConfigFactory.load("default"))
    private val durationSeconds = config.getInt("performance.durationSeconds")
    private val rampUpSeconds = config.getInt("performance.rampUpSeconds")
    private val rampDownSeconds = config.getInt("performance.rampDownSeconds")
    private val authentication = config.getString("performance.authorizationHeader")
    private val acceptHeader = config.getString("performance.acceptType")
    private val contentTypeHeader = config.getString("performance.contentType")
    private val rateMultiplier = config.getDouble("performance.rateMultiplier")
    private val instanceMultiplier = config.getDouble("performance.instanceMultiplier")

    // global assertion data
    private val globalResponseTimeMinLTE = config.getInt("performance.global.assertions.responseTime.min.lte")
    private val globalResponseTimeMinGTE = config.getInt("performance.global.assertions.responseTime.min.gte")
    private val globalResponseTimeMaxLTE = config.getInt("performance.global.assertions.responseTime.max.lte")
    private val globalResponseTimeMaxGTE = config.getInt("performance.global.assertions.responseTime.max.gte")
    private val globalResponseTimeMeanLTE = config.getInt("performance.global.assertions.responseTime.mean.lte")
    private val globalResponseTimeMeanGTE = config.getInt("performance.global.assertions.responseTime.mean.gte")
    private val globalResponseTimeFailedRequestsPercentLTE = config.getDouble("performance.global.assertions.failedRequests.percent.lte")
    private val globalResponseTimeFailedRequestsPercentGTE = config.getDouble("performance.global.assertions.failedRequests.percent.gte")
    private val globalResponseTimeSuccessfulRequestsPercentLTE = config.getDouble("performance.global.assertions.successfulRequests.percent.lte")
    private val globalResponseTimeSuccessfulRequestsPercentGTE = config.getDouble("performance.global.assertions.successfulRequests.percent.gte")

    // Setup http protocol configuration
    private val httpConf = http
        .baseUrl("http://localhost:8080")
        .doNotTrackHeader("1")
        .acceptLanguageHeader("en-US,en;q=0.5")
        .acceptEncodingHeader("gzip, deflate")
        .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
        .acceptHeader(acceptHeader)
        .contentTypeHeader(contentTypeHeader)

    // set authorization header if it has been modified from config
    if(!authentication.equals("~MANUAL_ENTRY")){
        httpConf.authorizationHeader(authentication)
    }

    // Setup all the operations per second for the test to ultimately be generated from configs
    private val apiEmployeeGetPerSecond = config.getDouble("performance.operationsPerSecond.apiEmployeeGet") * rateMultiplier * instanceMultiplier
    private val apiEmployeeIdRecordsGetPerSecond = config.getDouble("performance.operationsPerSecond.apiEmployeeIdRecordsGet") * rateMultiplier * instanceMultiplier
    private val apiEmployeePostPerSecond = config.getDouble("performance.operationsPerSecond.apiEmployeePost") * rateMultiplier * instanceMultiplier
    private val apiEmployeePutPerSecond = config.getDouble("performance.operationsPerSecond.apiEmployeePut") * rateMultiplier * instanceMultiplier
    private val apiEmployeeRecordsPostPerSecond = config.getDouble("performance.operationsPerSecond.apiEmployeeRecordsPost") * rateMultiplier * instanceMultiplier
    private val apiEmployeeRecordsPutPerSecond = config.getDouble("performance.operationsPerSecond.apiEmployeeRecordsPut") * rateMultiplier * instanceMultiplier
    private val apiProjectGetPerSecond = config.getDouble("performance.operationsPerSecond.apiProjectGet") * rateMultiplier * instanceMultiplier
    private val apiProjectPostPerSecond = config.getDouble("performance.operationsPerSecond.apiProjectPost") * rateMultiplier * instanceMultiplier
    private val apiProjectPutPerSecond = config.getDouble("performance.operationsPerSecond.apiProjectPut") * rateMultiplier * instanceMultiplier

    val scenarioBuilders: mutable.MutableList[PopulationBuilder] = new mutable.MutableList[PopulationBuilder]()

    // Set up CSV feeders
    private val nullQUERYFeeder = csv(userDataDirectory + File.separator + "apiEmployeeIdRecordsGet-queryParams.csv").random
    private val nullPATHFeeder = csv(userDataDirectory + File.separator + "apiEmployeeIdRecordsGet-pathParams.csv").random

    // Setup all scenarios


    private val scnapiEmployeeGet = scenario("apiEmployeeGetSimulation")
        .exec(http("apiEmployeeGet")
        .httpRequest("GET","/api/employee")
)

    // Run scnapiEmployeeGet with warm up and reach a constant rate for entire duration
    scenarioBuilders += scnapiEmployeeGet.inject(
        rampUsersPerSec(1) to(apiEmployeeGetPerSecond) during(rampUpSeconds),
        constantUsersPerSec(apiEmployeeGetPerSecond) during(durationSeconds),
        rampUsersPerSec(apiEmployeeGetPerSecond) to(1) during(rampDownSeconds)
    )


    private val scnapiEmployeeIdRecordsGet = scenario("apiEmployeeIdRecordsGetSimulation")
        .feed(nullQUERYFeeder)
        .feed(nullPATHFeeder)
        .exec(http("apiEmployeeIdRecordsGet")
        .httpRequest("GET","/api/employee/${id}/records")
        .queryParam("yearMonth","${yearMonth}")
)

    // Run scnapiEmployeeIdRecordsGet with warm up and reach a constant rate for entire duration
    scenarioBuilders += scnapiEmployeeIdRecordsGet.inject(
        rampUsersPerSec(1) to(apiEmployeeIdRecordsGetPerSecond) during(rampUpSeconds),
        constantUsersPerSec(apiEmployeeIdRecordsGetPerSecond) during(durationSeconds),
        rampUsersPerSec(apiEmployeeIdRecordsGetPerSecond) to(1) during(rampDownSeconds)
    )


    private val scnapiEmployeePost = scenario("apiEmployeePostSimulation")
        .exec(http("apiEmployeePost")
        .httpRequest("POST","/api/employee")
)

    // Run scnapiEmployeePost with warm up and reach a constant rate for entire duration
    scenarioBuilders += scnapiEmployeePost.inject(
        rampUsersPerSec(1) to(apiEmployeePostPerSecond) during(rampUpSeconds),
        constantUsersPerSec(apiEmployeePostPerSecond) during(durationSeconds),
        rampUsersPerSec(apiEmployeePostPerSecond) to(1) during(rampDownSeconds)
    )


    private val scnapiEmployeePut = scenario("apiEmployeePutSimulation")
        .exec(http("apiEmployeePut")
        .httpRequest("PUT","/api/employee")
)

    // Run scnapiEmployeePut with warm up and reach a constant rate for entire duration
    scenarioBuilders += scnapiEmployeePut.inject(
        rampUsersPerSec(1) to(apiEmployeePutPerSecond) during(rampUpSeconds),
        constantUsersPerSec(apiEmployeePutPerSecond) during(durationSeconds),
        rampUsersPerSec(apiEmployeePutPerSecond) to(1) during(rampDownSeconds)
    )


    private val scnapiEmployeeRecordsPost = scenario("apiEmployeeRecordsPostSimulation")
        .exec(http("apiEmployeeRecordsPost")
        .httpRequest("POST","/api/employee/records")
)

    // Run scnapiEmployeeRecordsPost with warm up and reach a constant rate for entire duration
    scenarioBuilders += scnapiEmployeeRecordsPost.inject(
        rampUsersPerSec(1) to(apiEmployeeRecordsPostPerSecond) during(rampUpSeconds),
        constantUsersPerSec(apiEmployeeRecordsPostPerSecond) during(durationSeconds),
        rampUsersPerSec(apiEmployeeRecordsPostPerSecond) to(1) during(rampDownSeconds)
    )


    private val scnapiEmployeeRecordsPut = scenario("apiEmployeeRecordsPutSimulation")
        .exec(http("apiEmployeeRecordsPut")
        .httpRequest("PUT","/api/employee/records")
)

    // Run scnapiEmployeeRecordsPut with warm up and reach a constant rate for entire duration
    scenarioBuilders += scnapiEmployeeRecordsPut.inject(
        rampUsersPerSec(1) to(apiEmployeeRecordsPutPerSecond) during(rampUpSeconds),
        constantUsersPerSec(apiEmployeeRecordsPutPerSecond) during(durationSeconds),
        rampUsersPerSec(apiEmployeeRecordsPutPerSecond) to(1) during(rampDownSeconds)
    )


    private val scnapiProjectGet = scenario("apiProjectGetSimulation")
        .exec(http("apiProjectGet")
        .httpRequest("GET","/api/project")
)

    // Run scnapiProjectGet with warm up and reach a constant rate for entire duration
    scenarioBuilders += scnapiProjectGet.inject(
        rampUsersPerSec(1) to(apiProjectGetPerSecond) during(rampUpSeconds),
        constantUsersPerSec(apiProjectGetPerSecond) during(durationSeconds),
        rampUsersPerSec(apiProjectGetPerSecond) to(1) during(rampDownSeconds)
    )


    private val scnapiProjectPost = scenario("apiProjectPostSimulation")
        .exec(http("apiProjectPost")
        .httpRequest("POST","/api/project")
)

    // Run scnapiProjectPost with warm up and reach a constant rate for entire duration
    scenarioBuilders += scnapiProjectPost.inject(
        rampUsersPerSec(1) to(apiProjectPostPerSecond) during(rampUpSeconds),
        constantUsersPerSec(apiProjectPostPerSecond) during(durationSeconds),
        rampUsersPerSec(apiProjectPostPerSecond) to(1) during(rampDownSeconds)
    )


    private val scnapiProjectPut = scenario("apiProjectPutSimulation")
        .exec(http("apiProjectPut")
        .httpRequest("PUT","/api/project")
)

    // Run scnapiProjectPut with warm up and reach a constant rate for entire duration
    scenarioBuilders += scnapiProjectPut.inject(
        rampUsersPerSec(1) to(apiProjectPutPerSecond) during(rampUpSeconds),
        constantUsersPerSec(apiProjectPutPerSecond) during(durationSeconds),
        rampUsersPerSec(apiProjectPutPerSecond) to(1) during(rampDownSeconds)
    )

    setUp(
        scenarioBuilders.toList
    ).protocols(httpConf).assertions(
        global.responseTime.min.lte(globalResponseTimeMinLTE),
        global.responseTime.min.gte(globalResponseTimeMinGTE),
        global.responseTime.max.lte(globalResponseTimeMaxLTE),
        global.responseTime.max.gte(globalResponseTimeMaxGTE),
        global.responseTime.mean.lte(globalResponseTimeMeanLTE),
        global.responseTime.mean.gte(globalResponseTimeMeanGTE),
        global.failedRequests.percent.lte(globalResponseTimeFailedRequestsPercentLTE),
        global.failedRequests.percent.gte(globalResponseTimeFailedRequestsPercentGTE),
        global.successfulRequests.percent.lte(globalResponseTimeSuccessfulRequestsPercentLTE),
        global.successfulRequests.percent.gte(globalResponseTimeSuccessfulRequestsPercentGTE)
    )
}
