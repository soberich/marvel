package com.example.marvel.domain.model.jpa.employee

data class EmployeeListingView(
    override val id                           : Long,
    override val name                         : String,
    override val email                        : String
) : EmployeeView
