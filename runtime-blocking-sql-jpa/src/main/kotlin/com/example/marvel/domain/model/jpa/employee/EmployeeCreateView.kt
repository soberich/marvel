package com.example.marvel.domain.model.jpa.employee

import com.blazebit.persistence.view.CreatableEntityView
import com.blazebit.persistence.view.EntityView
import com.blazebit.persistence.view.Mapping
import com.example.marvel.domain.model.api.employee.EmployeeDetailedView

@CreatableEntityView(validatePersistability = false)
@EntityView(EmployeeEntity::class)
interface EmployeeCreateView : EmployeeDetailedView {
    @set:Mapping("LOWER(name)")
    override var name                         : String
    override var email                        : String
}
