package com.example.marvel.domain.employee

import com.example.marvel.api.EmployeeDetailedView
import io.micronaut.core.annotation.Introspected

/**
 * Additional specializations of view (different projections) could be specified here.
 */
@Introspected
data class EmployeeDetailedViewDefault(
    //@formatter:off
    override val id                           : Long,
    override val name                         : String,
    override val email                        : String
    //@formatter:on
) : EmployeeDetailedView
