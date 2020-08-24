package com.example.marvel.domain.employee

import com.example.marvel.api.EmployeeDetailedView

/**
 * Additional specializations of view (different projections) could be specified here.
 */
data class EmployeeDetailedViewDefault(
    //@formatter:off
    override val id                           : Long,
    override val version                      : Int,
    override val name                         : String,
    override val email                        : String
    //@formatter:on
) : EmployeeDetailedView
