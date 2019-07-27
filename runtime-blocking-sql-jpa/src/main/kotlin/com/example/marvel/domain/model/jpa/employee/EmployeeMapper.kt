package com.example.marvel.domain.model.jpa.employee

import com.blazebit.persistence.view.EntityViewManager
import com.example.marvel.domain.model.api.employee.EmployeeCreateCommand
import com.example.marvel.domain.model.api.employee.EmployeeUpdateCommand
import com.example.marvel.domain.model.jpa.MapperConfig
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.ObjectFactory
import javax.inject.Inject


@Mapper(config = MapperConfig::class)
abstract class EmployeeMapper {

    @Inject
    internal lateinit var evm: EntityViewManager

    @ObjectFactory
    fun objectFactory(): EmployeeCreateView = evm.create(EmployeeCreateView::class.java)

    @ObjectFactory
    fun objectFactory(@Context employeeId: Long): EmployeeUpdateView = evm.getReference(EmployeeUpdateView::class.java, employeeId)


    abstract fun toEntity(source: EmployeeCreateCommand): EmployeeCreateView

    abstract fun toEntity(@Context employeeId: Long, source: EmployeeUpdateCommand): EmployeeUpdateView
}
