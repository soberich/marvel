package com.example.marvel.domain.model.jpa.project

import com.example.marvel.domain.model.api.record.RecordCreateCommand
import com.example.marvel.domain.model.api.record.RecordUpdateCommand
import com.example.marvel.domain.model.jpa.GenericMapper
import com.example.marvel.domain.model.jpa.MapperConfig
import com.example.marvel.domain.model.jpa.record.RecordCreateView
import com.example.marvel.domain.model.jpa.record.RecordEntity
import com.example.marvel.domain.model.jpa.record.RecordUpdateView
import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionMapper
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import java.io.Serializable

@Mapper(config = MapperConfig::class, uses = [RecordCollectionMapper::class])
abstract class ProjectMapper : GenericMapper<ProjectEntity>() {

//    @Mappings(
//        Mapping(target = "id", ignore = true))
//    abstract fun toEntity(source: ProspectCreateCommand): ProspectEntity
//
//    @Mappings(
//        Mapping(target = "id", ignore = true))
//    abstract fun toEntity(@Context id: Serializable, source: ProspectUpdateCommand): ProspectEntity
//
//    abstract fun toCreateView(source: ProspectEntity): ProspectCreateView
//
//    abstract fun toUpdateView(source: ProspectEntity): ProspectUpdateView
}
