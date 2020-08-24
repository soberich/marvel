package com.example.marvel.domain.project

import com.example.marvel.api.ProjectCommand
import com.example.marvel.api.ProjectCreateView
import com.example.marvel.api.ProjectUpdateView
import com.example.marvel.domain.IgnoreAuditInfo
import com.example.marvel.domain.MapperConfig
import com.example.marvel.domain.recordcollection.RecordCollectionMapper
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(config = MapperConfig::class, uses = [RecordCollectionMapper::class])
abstract class ProjectMapper {

    @Mappings(
        Mapping(ignore = true  , target = "version"),
        Mapping(source = "name", target = "id"))
    @IgnoreAuditInfo
    abstract fun toEntity(source: ProjectCommand.ProjectCreateCommand): ProjectEntity

    @Mapping(source = "name", target = "id")
    @IgnoreAuditInfo
    abstract fun toEntity(@Context id: String, source: ProjectCommand.ProjectUpdateCommand): ProjectEntity

    @Mapping(source = "id", target = "name")
    abstract fun toCreateView(source: ProjectEntity): ProjectCreateView

    @Mapping(source = "id", target = "name")
    abstract fun toUpdateView(source: ProjectEntity): ProjectUpdateView
}
