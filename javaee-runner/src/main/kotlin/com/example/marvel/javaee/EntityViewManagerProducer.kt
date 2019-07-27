package com.example.marvel.javaee

import com.blazebit.persistence.CriteriaBuilderFactory
import com.blazebit.persistence.view.EntityViewManager
import com.blazebit.persistence.view.spi.EntityViewConfiguration
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.context.Initialized
import javax.enterprise.event.Observes
import javax.enterprise.inject.Produces
import javax.inject.Inject

@ApplicationScoped
class EntityViewManagerProducer @Inject constructor(
    private val config: EntityViewConfiguration,
    private val criteriaBuilderFactory: CriteriaBuilderFactory
) {

    @get:
    [Produces
    ApplicationScoped]
    @Volatile
    protected lateinit var evm: EntityViewManager

    fun init(@Observes @Initialized(ApplicationScoped::class) init: Any?) {
        // do some configuration
        evm = config!!.createEntityViewManager(criteriaBuilderFactory)
    }
}
