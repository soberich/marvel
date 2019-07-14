package com.example.marvel.web.rest.jakarta

import com.blazebit.persistence.CriteriaBuilderFactory
import com.blazebit.persistence.view.EntityViewManager
import com.blazebit.persistence.view.EntityViewSetting
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/hello")
class GreetingResource {

    @PersistenceContext
    internal var em: EntityManager? = null

    @Inject
    private val cbf: CriteriaBuilderFactory? = null
    @Inject
    private val evm: EntityViewManager? = null

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): List<PojoView> {
        val cb = cbf!!.create(em, Pojo::class.java)
        val catNameBuilder = evm!!.applySetting(EntityViewSetting.create(PojoView::class.java), cb)
        val catNameViews = catNameBuilder.getResultList()
        println("catNameViews = $catNameViews")
        return catNameViews
    }
}
