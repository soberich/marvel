package com.example.marvel.domain.model.jpa.employee

import com.example.marvel.domain.model.api.employee.EmployeeCreateCommand
import com.example.marvel.domain.model.api.employee.EmployeeUpdateCommand
import com.example.marvel.domain.model.jpa.GenericMapper
import com.example.marvel.domain.model.jpa.MapperConfig
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import java.io.Serializable


@Mapper(config = MapperConfig::class)
abstract class EmployeeMapper : GenericMapper<EmployeeEntity>() {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "records", ignore = true))
    abstract fun toEntity(source: EmployeeCreateCommand): EmployeeEntity

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "records", ignore = true))
    abstract fun toEntity(@Context id: Serializable, source: EmployeeUpdateCommand): EmployeeEntity

    abstract fun toCreateView(source: EmployeeEntity): EmployeeCreateView

    abstract fun toUpdateView(source: EmployeeEntity): EmployeeUpdateView
}
