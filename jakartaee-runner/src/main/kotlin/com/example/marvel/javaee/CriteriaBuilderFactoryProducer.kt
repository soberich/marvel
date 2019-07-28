package com.example.marvel.javaee

import com.blazebit.persistence.Criteria
import com.blazebit.persistence.CriteriaBuilderFactory

import com.blazebit.persistence.spi.CriteriaBuilderConfiguration
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.context.Initialized
import javax.enterprise.event.Observes
import javax.enterprise.inject.Produces
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceUnit

@ApplicationScoped
class CriteriaBuilderFactoryProducer {

    @set:
    [PersistenceUnit]
    protected lateinit var entityManagerFactory: EntityManagerFactory

    @get:
    [Produces
    ApplicationScoped]
    @Volatile
    protected lateinit var criteriaBuilderFactory: CriteriaBuilderFactory

    fun init(@Observes @Initialized(ApplicationScoped::class) init: Any?) {
        val config: CriteriaBuilderConfiguration = Criteria.getDefault()
        // do some configuration

        criteriaBuilderFactory = config.createCriteriaBuilderFactory(entityManagerFactory)
    }
}
