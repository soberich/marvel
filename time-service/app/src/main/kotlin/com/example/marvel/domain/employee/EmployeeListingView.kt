package com.example.marvel.domain.employee

import com.blazebit.persistence.view.EntityView
import com.example.marvel.api.EmployeeView

@EntityView(EmployeeEntity::class)
interface EmployeeListingView : EmployeeView
