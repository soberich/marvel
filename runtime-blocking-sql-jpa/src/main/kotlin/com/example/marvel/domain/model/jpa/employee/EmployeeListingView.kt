package com.example.marvel.domain.model.jpa.employee

import com.example.marvel.domain.model.api.employee.EmployeeView

data class EmployeeListingView(
    override val id                           : Long,
    override val name                         : String,
    override val email                        : String
) : EmployeeView
