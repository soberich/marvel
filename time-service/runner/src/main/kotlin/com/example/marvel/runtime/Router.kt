//@file:Suppress("NO_NOARG_CONSTRUCTOR_IN_SUPERCLASS")
//
//package com.example.marvel.runtime
//
////import io.ktor.server.engine.*
////import io.ktor.server.netty.*
//import com.example.marvel.api.EmployeeResourceAdapter
//import com.example.marvel.api.ProjectResourceAdapter
//import com.example.marvel.convention.serial.Json
//import io.ktor.application.*
//import io.ktor.http.*
//import io.ktor.request.*
//import io.ktor.response.*
//import io.ktor.routing.*
//import io.ktor.util.*
//import io.micronaut.ktor.KtorRoutingBuilder
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.future.await
//import kotlinx.coroutines.reactive.collect
//import java.time.YearMonth
//import javax.inject.Inject
//import javax.inject.Named
//import javax.inject.Singleton
//import javax.validation.Validator
//import kotlin.reflect.jvm.javaMethod
//
//@Named
//@Singleton
//class Router @Inject constructor(
//    private val employees: EmployeeResourceAdapter,
//    private val projects : ProjectResourceAdapter,
//    private val validator: Validator
//) : KtorRoutingBuilder({
//
//    route("/api") {
//
//        route("/employee") {
//
//            get {
//                call.respondTextWriter(ContentType.Application.Json) {
//                    val writer = this
//                    employees.getEmployees()
//                        .collect {
//                            writer.write(Json.CONFIGURED_MAPPER.writeValueAsString(it))
//                            writer.flush()
//                            delay(100L)
//                        }
//                }
//            }
//
//            post {
//                call.respond(employees.createEmployee(call.receive()).await())
//            }
//
//            put {
//                call.respond(employees.updateEmployee(call.receive()).await())
//            }
//
//            get("{id}/records") {
//                val id        : Long      by call.parameters
//                val yearMonth : YearMonth by call.request.queryParameters
//                val errors =
//                    validator
//                        .forExecutables()
//                        .validateParameters(employees, employees::getForPeriod.javaMethod, arrayOf(id, yearMonth))
//                call.respond(errors.takeIf { it.isNotEmpty() } ?: employees.getForPeriod(id, yearMonth))
//            }
//
//            post("/records") {
//                call.respond(employees.saveWholePeriod(call.receive()).await())
//            }
//
//            put("/records") {
//                call.respond(employees.adjustWholePeriod(call.receive()).await())
//            }
//        }
//
//        route("/project") {
//
//            get {
//                call.respond(projects.getProjects())
//            }
//
//            post {
//                call.respond(projects.createProject(call.receive()).await())
//            }
//
//            put {
//                call.respond(projects.updateProject(call.receive()).await())
//            }
//        }
//    }
//})
