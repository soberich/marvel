package com.example.marvel.domain.recordcollection

import com.example.marvel.api.RecordCollectionCommand.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionUpdateCommand
import com.example.marvel.api.RecordCollectionCreateView
import com.example.marvel.api.RecordCollectionUpdateView
import com.example.marvel.api.RecordCommand
import com.example.marvel.domain.IgnoreAuditInfo
import com.example.marvel.domain.MapperConfig
import com.example.marvel.domain.employee.EmployeeMapper
import com.example.marvel.domain.project.ProjectMapper
import com.example.marvel.domain.record.RecordEntity
import com.example.marvel.domain.record.RecordMapper
import org.mapstruct.*


@Mapper(
    config = MapperConfig::class,
    uses = [
        EmployeeMapper::class,
        ProjectMapper::class,
        RecordMapper::class])
abstract class RecordCollectionMapper {

    @Mappings(
        Mapping(ignore = true        , target = "id"),
        Mapping(ignore = true        , target = "version"),
        Mapping(source = "employeeId", target = "employee"),
        Mapping(source = "projectId" , target = "project"),
        Mapping(source = "records"   , target = "records"  , qualifiedByName = ["recordChildIgnoresParent"]))
    @IgnoreAuditInfo
    abstract fun toEntity(source: RecordCollectionCreateCommand): RecordCollectionEntity

    @Mappings(
        Mapping(ignore = true     , target = "id"),
        Mapping(ignore = true     , target = "employee"),
        Mapping(ignore = true     , target = "project"),
        Mapping(source = "records", target = "records"  , qualifiedByName = ["recordChildIgnoresParent"]))
    @IgnoreAuditInfo
    abstract fun toEntity(source: RecordCollectionUpdateCommand, @MappingTarget target: RecordCollectionEntity): RecordCollectionEntity

    @Named("recordChildIgnoresParent")
    @Mappings(
        Mapping(ignore = true, target = "version"),
        Mapping(ignore = true, target = "report"))
    @RecordMapper.RecordToEntity
    abstract fun toEntity(source: RecordCommand.RecordCreateCommand): RecordEntity

    @Named("recordChildIgnoresParent")
    @Mappings(
        Mapping(ignore = true, target = "version"),
        Mapping(ignore = true, target = "report"))
    @RecordMapper.RecordToEntity
    abstract fun toEntity(source: RecordCommand.RecordUpdateCommand): RecordEntity

    @Named("recordChildIgnoresParent")
    @IterableMapping(qualifiedByName = ["recordChildIgnoresParent"])
    abstract fun toEntityCreate(source: List<RecordCommand.RecordCreateCommand>): MutableSet<RecordEntity>

    @Named("recordChildIgnoresParent")
    @IterableMapping(qualifiedByName = ["recordChildIgnoresParent"])
    abstract fun toEntityUpdate(source: List<RecordCommand.RecordUpdateCommand>): MutableSet<RecordEntity>

    @AfterMapping
    open fun syncParent(@MappingTarget target: RecordCollectionEntity) = target.records.forEach { it.report = target }

    @Mappings(
        Mapping(source = "project.id" , target = "projectId"),
        Mapping(source = "employee.id", target = "employeeId"))
    abstract fun toCreateView(source: RecordCollectionEntity): RecordCollectionCreateView

    @Mappings(
        Mapping(source = "project.id" , target = "projectId"),
        Mapping(source = "employee.id", target = "employeeId"))
    abstract fun toUpdateView(source: RecordCollectionEntity): RecordCollectionUpdateView
}
