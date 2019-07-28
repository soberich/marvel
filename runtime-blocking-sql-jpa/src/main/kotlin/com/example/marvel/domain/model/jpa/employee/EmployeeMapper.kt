package com.example.marvel.domain.model.jpa.employee

import com.example.marvel.domain.model.api.employee.EmployeeCreateCommand
import com.example.marvel.domain.model.api.employee.EmployeeUpdateCommand
import com.example.marvel.domain.model.jpa.GenericMapper
import com.example.marvel.domain.model.jpa.MapperConfig
import org.mapstruct.Mapper


@Mapper(config = MapperConfig::class)
abstract class EmployeeMapper : GenericMapper<EmployeeCreateView, EmployeeCreateCommand, EmployeeUpdateView, EmployeeUpdateCommand>()
