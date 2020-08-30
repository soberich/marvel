package com.example.marvel.domain.employee

import com.blazebit.persistence.view.EntityView
import com.blazebit.persistence.view.IdMapping
import com.example.marvel.api.EmployeeView

@EntityView(EmployeeEntity::class)
interface EmployeeListingView : EmployeeView {
    @get:IdMapping
    override val id: Long
}
