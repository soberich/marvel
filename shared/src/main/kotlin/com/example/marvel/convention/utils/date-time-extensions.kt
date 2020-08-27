@file:
[JvmName("DateTimeConverterUtils")
Suppress("NOTHING_TO_INLINE", "unused")]

package com.example.marvel.convention.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

/**
 * We should prefer not to use these helpers as it again couples use with some external "helpers"
 * which overtime may lead to more and more coupling and code inflexibility to changes as introducing those changes
 * won't be straightforward as we depend on something external.
 * A typical example is corporate "super-useful-library" which overtime simply bounds everything together as big ball of mud.
 * Goal is to ease this for now
 * ```
 *   @Path("{id}")
 *   @GET
 *   @Produces("application/json")
 *   public Employee getById(@PathParam("id") String id){
 *       Employee employee = em.find(Employee.class, id); //get employee info from backend DB
 *       Date lastModified = employee.getLastModified(); //get last modified date
 *
 *       ResponseBuilder evaluationResultBuilder = request.evaluatePreconditions(lastModified); //let JAX-RS do the math!
 *
 *       if(evaluationResultBuilder == null) {
 *           return employee; //resource was modified, send latest info (and HTTP 200 status)
 *       }
 *       CacheControl caching = ...; //decide caching semantics
 *       evaluationResultBuilder.cacheControl(caching).header("Last-Modified",lastModified); //add metadata
 *       throw new WebApplicationException(evaluationResultBuilder.build());
 *   }
 * ```
 */
inline fun LocalDate.asDate(zone: ZoneId = ZoneId.systemDefault()): Date = Date.from(atStartOfDay().atZone(zone).toInstant())

inline fun LocalDateTime.asDate(zone: ZoneId = ZoneId.systemDefault()): Date = Date.from(atZone(zone).toInstant())

inline fun Date.asLocalDate(zone: ZoneId = ZoneId.systemDefault()): LocalDate = Instant.ofEpochMilli(time).atZone(zone).toLocalDate()

inline fun Date.asLocalDateTime(zone: ZoneId = ZoneId.systemDefault()): LocalDateTime = Instant.ofEpochMilli(time).atZone(zone).toLocalDateTime()
