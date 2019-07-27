package com.example.marvel.domain.model.jpa.employee

import com.blazebit.persistence.view.EntityView
import com.blazebit.persistence.view.Mapping
import com.blazebit.persistence.view.UpdatableEntityView
import com.example.marvel.domain.model.api.employee.EmployeeDetailedView

@UpdatableEntityView
@EntityView(EmployeeEntity::class)
interface EmployeeUpdateView : EmployeeDetailedView {
    @set:Mapping("UPPER(name)")
    override var name                         : String
    override var email                        : String
}
