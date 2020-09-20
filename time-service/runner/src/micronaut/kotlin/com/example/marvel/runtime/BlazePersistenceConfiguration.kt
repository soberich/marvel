package com.example.marvel.runtime

import com.blazebit.persistence.Criteria
import com.blazebit.persistence.CriteriaBuilderFactory
import com.blazebit.persistence.view.EntityViewManager
import com.blazebit.persistence.view.EntityViews
import com.example.marvel.api.*
import com.example.marvel.domain.employee.EmployeeDetailedViewDefault
import com.example.marvel.domain.employee.EmployeeListingView
import com.example.marvel.domain.record.RecordDetailedViewDefault
import com.example.marvel.domain.record.RecordIdView
import com.example.marvel.domain.record.RecordListingView
import com.example.marvel.domain.recordcollection.RecordCollectionDetailedViewDefault
import com.example.marvel.domain.recordcollection.RecordCollectionListingView
import io.micronaut.core.annotation.Introspected
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Scope
import javax.inject.Inject
import javax.persistence.EntityManagerFactory

@Introspected(
    classes = [
        EmployeeCreateCommand::class,
        EmployeeUpdateCommand::class,
        ProjectCreateCommand::class,
        ProjectUpdateCommand::class,
        RecordCollectionCreateCommand::class,
        RecordCollectionUpdateCommand::class,
        RecordCreateCommand::class,
        RecordUpdateCommand::class,
    ]
)
@EntityScan("com.example")
@Configuration(proxyBeanMethods = false)
class BlazePersistenceConfiguration {

    @set:
    [Inject]
    protected lateinit var emf: EntityManagerFactory

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Lazy(false)
    fun createCriteriaBuilderFactory(): CriteriaBuilderFactory {
        val config = Criteria.getDefault()
        // do some configuration

        return config.createCriteriaBuilderFactory(emf)
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Lazy(false) // inject the criteria builder factory which will be used along with the entity view manager
    fun createEntityViewManager(
        cbf: CriteriaBuilderFactory?
    ): EntityViewManager? {
        val cfg = EntityViews.createDefaultConfiguration()

        cfg.addEntityView(EmployeeDetailedViewDefault::class.java)
        cfg.addEntityView(EmployeeListingView::class.java)
        cfg.addEntityView(RecordCollectionDetailedViewDefault::class.java)
        cfg.addEntityView(RecordCollectionListingView::class.java)
        cfg.addEntityView(RecordDetailedViewDefault::class.java)
        cfg.addEntityView(RecordIdView::class.java)
        cfg.addEntityView(RecordListingView::class.java)
        cfg.addEntityView(RecordListingView::class.java)

        return cfg.createEntityViewManager(cbf)
    }
}
