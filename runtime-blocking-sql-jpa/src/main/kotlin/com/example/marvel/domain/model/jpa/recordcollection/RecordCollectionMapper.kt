package com.example.marvel.domain.model.jpa.recordcollection

import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import com.example.marvel.domain.model.jpa.GenericMapper
import com.example.marvel.domain.model.jpa.MapperConfig
import com.example.marvel.domain.model.jpa.employee.EmployeeMapper
import com.example.marvel.domain.model.jpa.record.RecordMapper
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import java.io.Serializable

@Mapper(config = MapperConfig::class, uses = [EmployeeMapper::class, RecordMapper::class])
abstract class RecordCollectionMapper : GenericMapper<RecordCollectionEntity>() {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(source = "employeeId", target = "employee.id"),
        Mapping(source = "source.projectId", target = "project.id"))
    abstract fun toEntity(employeeId: Long?, source: RecordCollectionCreateCommand): RecordCollectionEntity

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(source = "employeeId", target = "employee.id"),
        Mapping(source = "projectId", target = "project.id"))
    abstract fun toEntity(@Context id: Serializable, source: RecordCollectionUpdateCommand): RecordCollectionEntity

    @Mappings(
        Mapping(source = "project.id", target = "projectId"),
        Mapping(source = "employee.id", target = "employeeId"))
    abstract fun toCreateView(source: RecordCollectionEntity): RecordCollectionCreateView

    @Mappings(
        Mapping(source = "project.id", target = "projectId"),
        Mapping(source = "employee.id", target = "employeeId"))
    abstract fun toUpdateView(source: RecordCollectionEntity): RecordCollectionUpdateView
}
