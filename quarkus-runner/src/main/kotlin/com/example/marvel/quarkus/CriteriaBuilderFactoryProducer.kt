package com.example.marvel.quarkus

import com.blazebit.persistence.Criteria
import com.blazebit.persistence.CriteriaBuilderFactory
import io.quarkus.runtime.StartupEvent
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Named
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceUnit

@Named
@ApplicationScoped
class CriteriaBuilderFactoryProducer {

    @PersistenceUnit(name = "default")
    private val entityManagerFactory: EntityManagerFactory? = null

    private var criteriaBuilderFactory: CriteriaBuilderFactory? = null

    @PostConstruct
    fun init(/*@Observes ev: StartupEvent*/) {
        val config = Criteria.getDefault()
        // do some configuration
        this.criteriaBuilderFactory = config.createCriteriaBuilderFactory(entityManagerFactory)
    }

    @Named
    @Produces
    @ApplicationScoped
    fun createCriteriaBuilderFactory(): CriteriaBuilderFactory? {
        return criteriaBuilderFactory
    }
}
