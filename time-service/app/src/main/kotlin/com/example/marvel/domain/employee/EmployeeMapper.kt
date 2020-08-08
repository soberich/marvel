package com.example.marvel.domain.employee

import com.example.marvel.api.EmployeeCommand
import com.example.marvel.api.EmployeeCreateView
import com.example.marvel.api.EmployeeUpdateView
import com.example.marvel.domain.MapperConfig
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import java.io.Serializable

@Mapper(config = MapperConfig::class)
abstract class EmployeeMapper {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "createdBy", ignore = true),
        Mapping(target = "createdDate", ignore = true),
        Mapping(target = "lastModifiedBy", ignore = true),
        Mapping(target = "lastModifiedDate", ignore = true),
        Mapping(target = "records", ignore = true))
    abstract fun toEntity(source: EmployeeCommand.EmployeeCreateCommand): EmployeeEntity

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "createdBy", ignore = true),
        Mapping(target = "createdDate", ignore = true),
        Mapping(target = "lastModifiedBy", ignore = true),
        Mapping(target = "lastModifiedDate", ignore = true),
        Mapping(target = "records", ignore = true))
    abstract fun toEntity(@Context id: Serializable, source: EmployeeCommand.EmployeeUpdateCommand): EmployeeEntity

    abstract fun toCreateView(source: EmployeeEntity): EmployeeCreateView

    abstract fun toUpdateView(source: EmployeeEntity): EmployeeUpdateView
}
