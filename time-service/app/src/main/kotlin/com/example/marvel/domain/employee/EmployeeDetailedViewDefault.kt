package com.example.marvel.domain.employee

import com.example.marvel.api.EmployeeDetailedView

/**
 * Additional specializations of view (different projections) could be specified here.
 */
data class EmployeeDetailedViewDefault(
    override val id                           : Long,
    override val name                         : String,
    override val email                        : String
) : EmployeeDetailedView
