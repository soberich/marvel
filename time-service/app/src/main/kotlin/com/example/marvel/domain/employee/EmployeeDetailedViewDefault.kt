package com.example.marvel.domain.employee

import com.blazebit.persistence.view.EntityView
import com.example.marvel.api.EmployeeDetailedView

/**
 * Additional specializations of view (different projections) could be specified here.
 */
@EntityView(EmployeeEntity::class)
interface EmployeeDetailedViewDefault : EmployeeDetailedView
