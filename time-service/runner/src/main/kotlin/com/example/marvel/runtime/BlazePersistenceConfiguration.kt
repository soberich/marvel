package com.example.marvel.runtime

import com.blazebit.persistence.Criteria
import com.blazebit.persistence.CriteriaBuilderFactory
import com.blazebit.persistence.integration.hibernate.base.spi.HibernateVersionProvider
import com.blazebit.persistence.view.EntityViewManager
import com.blazebit.persistence.view.EntityViews
import com.example.marvel.domain.employee.EmployeeDetailedViewDefault
import com.example.marvel.domain.employee.EmployeeListingView
import org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Scope
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceUnit


@Configuration
class BlazePersistenceConfiguration : HibernateVersionProvider {

    @set:
    [PersistenceUnit]
    protected lateinit var emf: EntityManagerFactory

    @Bean
    @Scope(SCOPE_SINGLETON)
//    @Lazy(false)
    fun createCriteriaBuilderFactory(): CriteriaBuilderFactory {
        val config = Criteria.getDefault()
        // do some configuration

        return config.createCriteriaBuilderFactory(emf)
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
//    @Lazy(false) // inject the criteria builder factory which will be used along with the entity view manager
    fun createEntityViewManager(
        cbf: CriteriaBuilderFactory?
    ): EntityViewManager? {
        val cfg = EntityViews.createDefaultConfiguration()

        cfg.addEntityView(EmployeeDetailedViewDefault::class.java)
        cfg.addEntityView(EmployeeListingView::class.java)

        return cfg.createEntityViewManager(cbf)
    }

    override fun getVersion(): String = "5.4.20.Final"
}
