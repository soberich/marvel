package com.example.marvel.web.rest.jakarta

import com.blazebit.persistence.CriteriaBuilderFactory
import com.blazebit.persistence.view.EntityViewManager
import com.blazebit.persistence.view.impl.EntityViewConfigurationImpl
import com.blazebit.persistence.view.spi.EntityViewConfiguration
import io.quarkus.runtime.StartupEvent
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.enterprise.inject.Produces
import javax.inject.Inject


@ApplicationScoped
class EntityViewManagerProducer {

    // inject the configuration provided by the cdi integration
//    @Inject
    private val config: EntityViewConfiguration? =  EntityViewConfigurationImpl()

    // inject the criteria builder factory which will be used along with the entity view manager
    @Inject
    private val criteriaBuilderFactory: CriteriaBuilderFactory? = null

    private var evm: EntityViewManager? = null

    fun init(@Observes event: StartupEvent) {
        // do some configuration
        evm = config!!.createEntityViewManager(criteriaBuilderFactory)
    }

    @Produces
    @ApplicationScoped
    fun entityViewConfiguration(): EntityViewConfiguration? {
        return config
    }

    @Produces
    @ApplicationScoped
    fun createEntityViewManager(): EntityViewManager? {
        return evm
    }
}
