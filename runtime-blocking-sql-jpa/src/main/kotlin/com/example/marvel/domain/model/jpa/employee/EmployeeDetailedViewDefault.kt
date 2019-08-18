package com.example.marvel.domain.model.jpa.employee

import com.example.marvel.domain.model.api.employee.EmployeeDetailedView

/**
 * Additional specializations of view (different projections) could be specified here.
 */
data class EmployeeDetailedViewDefault(
    override val id                           : Long,
    override val name                         : String,
    override val email                        : String
) : EmployeeDetailedView
