package com.example.marvel.domain.model.jpa.employee

import com.example.marvel.api.EmployeeView

data class EmployeeListingView(
    override val id                           : Long,
    override val name                         : String,
    override val email                        : String
) : EmployeeView
