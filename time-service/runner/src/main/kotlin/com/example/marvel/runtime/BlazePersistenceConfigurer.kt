package com.example.marvel.runtime

import com.blazebit.persistence.spi.CriteriaBuilderConfiguration
import com.blazebit.persistence.view.ConfigurationProperties.PROXY_UNSAFE_ALLOWED
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

@ApplicationScoped
class BlazePersistenceConfigurer {
    fun configure(@Observes config: CriteriaBuilderConfiguration) {
        config.setProperty(PROXY_UNSAFE_ALLOWED, "false")
    }

    //    public void configure(@Observes EntityViewConfiguration config) {
    //        // Register custom BasicUserType or register type test values
    //        config.registerBasicUserType(MyClass.class, MyClassBasicUserType.class);
    //    }
}
