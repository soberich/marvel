package com.example.marvel.config.nosql


import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

//@DataRegistry([
//    RecordImpl::class])
//    ProjectImpl::class,
//    EmployeeImpl::class,
//    RecordCollectionImpl::class])
@ApplicationScoped
class DatabaseConfig {

    @get:Produces
    @get:ApplicationScoped
    val client: CoroutineClient = KMongo.createClient().coroutine

    @get:Produces
    @get:ApplicationScoped
    val db: CoroutineDatabase get() = client.getDatabase("default")
}
