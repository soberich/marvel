package com.example.marvel.domain.employee

import com.example.marvel.api.*
import com.example.marvel.domain.IgnoreAuditInfo
import com.example.marvel.domain.MapperConfig
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import kotlin.annotation.AnnotationRetention.BINARY

@Mapper(config = MapperConfig::class)
abstract class EmployeeMapper {

    @Mappings(
        Mapping(ignore = true, target = "id"),
        Mapping(ignore = true, target = "version"))
    @EmployeeToEntity
    abstract fun toEntity(source: EmployeeCreateCommand): EmployeeEntity

    @EmployeeToEntity
    abstract fun toEntity(@Context id: Long, source: EmployeeUpdateCommand): EmployeeEntity

    abstract fun toCreateView(source: EmployeeEntity): EmployeeCreateView

    abstract fun toUpdateView(source: EmployeeEntity): EmployeeUpdateView

    @Mappings(
        Mapping(ignore = true, target = "records"))
    @IgnoreAuditInfo
    @Retention(BINARY)
    annotation class EmployeeToEntity
}
