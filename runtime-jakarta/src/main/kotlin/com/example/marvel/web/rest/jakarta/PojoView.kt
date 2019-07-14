package com.example.marvel.web.rest.jakarta

import com.blazebit.persistence.view.EntityView
import com.blazebit.persistence.view.IdMapping

@EntityView(Pojo::class)
interface PojoView {

    @get:IdMapping
    val id: Long?

    val name: String


}
