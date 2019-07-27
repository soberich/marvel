package com.example.marvel.quarkus

import com.blazebit.persistence.CriteriaBuilderFactory
import com.blazebit.persistence.view.EntityViewManager
import com.blazebit.persistence.view.impl.ConfigurationProperties.PROXY_UNSAFE_ALLOWED
import com.blazebit.persistence.view.impl.EntityViewConfigurationImpl
import com.blazebit.persistence.view.spi.EntityViewConfiguration
import io.quarkus.runtime.StartupEvent
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Inject
import javax.inject.Named

@Named
@ApplicationScoped
class EntityViewManagerProducer {

    // inject the configuration provided by the cdi integration
    //@Inject
    private val config: EntityViewConfiguration? = EntityViewConfigurationImpl()

    // inject the criteria builder factory, which will be used along with the entity view manager
    @Inject
    private val criteriaBuilderFactory: CriteriaBuilderFactory? = null

    private lateinit var evm: EntityViewManager

    @PostConstruct
    fun init(@Observes ev: StartupEvent) {
        config!!.setProperty(PROXY_UNSAFE_ALLOWED, "false")
        evm = config!!.createEntityViewManager(criteriaBuilderFactory)
    }

    @Named
    @Produces
    @ApplicationScoped
    fun entityViewConfiguration(): EntityViewConfiguration? {
        return config
    }

    @Named
    @Produces
    @ApplicationScoped
    fun createEntityViewManager(): EntityViewManager? {
        return evm
    }
}
