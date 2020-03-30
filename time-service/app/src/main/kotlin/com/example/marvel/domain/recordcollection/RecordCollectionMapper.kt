package com.example.marvel.domain.recordcollection

import com.example.marvel.api.RecordCollectionCommand.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionUpdateCommand
import com.example.marvel.api.RecordCollectionCreateView
import com.example.marvel.api.RecordCollectionUpdateView
import com.example.marvel.domain.GenericMapper
import com.example.marvel.domain.MapperConfig
import com.example.marvel.domain.employee.EmployeeMapper
import com.example.marvel.domain.record.RecordMapper
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import java.io.Serializable

@Mapper(config = MapperConfig::class, uses = [EmployeeMapper::class, RecordMapper::class])
abstract class RecordCollectionMapper : GenericMapper<RecordCollectionEntity>() {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "createdBy", ignore = true),
        Mapping(target = "createdDate", ignore = true),
        Mapping(target = "lastModifiedBy", ignore = true),
        Mapping(target = "lastModifiedDate", ignore = true),
        Mapping(source = "employeeId", target = "employee.id"),
        Mapping(source = "projectId", target = "project.id"))
    abstract fun toEntity(source: RecordCollectionCreateCommand): RecordCollectionEntity

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "createdBy", ignore = true),
        Mapping(target = "createdDate", ignore = true),
        Mapping(target = "lastModifiedBy", ignore = true),
        Mapping(target = "lastModifiedDate", ignore = true),
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
