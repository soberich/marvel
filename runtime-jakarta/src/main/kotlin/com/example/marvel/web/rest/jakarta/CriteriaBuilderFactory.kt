package com.example.marvel.web.rest.jakarta

import com.blazebit.persistence.Criteria
import com.blazebit.persistence.CriteriaBuilderFactory
import io.quarkus.runtime.StartupEvent
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.enterprise.inject.Produces
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceUnit


@ApplicationScoped
class CriteriaBuilderFactoryProducer {

    // inject your entity manager factory
    @PersistenceUnit(name = "default")
    private val entityManagerFactory: EntityManagerFactory? = null

    private var criteriaBuilderFactory: CriteriaBuilderFactory? = null

    fun init(@Observes event: StartupEvent) {
        val config = Criteria.getDefault()
        // do some configuration
        this.criteriaBuilderFactory = config.createCriteriaBuilderFactory(entityManagerFactory)
    }

    @Produces
    @ApplicationScoped
    fun createCriteriaBuilderFactory(): CriteriaBuilderFactory? {
        return criteriaBuilderFactory
    }
}
