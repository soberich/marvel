package com.example.marvel.domain.model.jpa.record

import com.example.marvel.domain.model.jpa.GenericMapper
import com.example.marvel.domain.model.jpa.MapperConfig
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(config = MapperConfig::class)
abstract class RecordMapper : GenericMapper<RecordEntity>() {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(source = "recordCollectionId", target = "report.id"))
    abstract fun toEntity(source: RecordCommand.RecordCreateCommand): RecordEntity

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(source = "id", target = "report.id"))
    abstract fun toEntity(source: RecordCommand.RecordUpdateCommand): RecordEntity

    @Mappings(
        Mapping(source = "report.id", target = "recordCollectionId"))
    abstract fun toCreateView(source: RecordEntity): RecordCreateView

    @Mappings(
        Mapping(source = "report.id", target = "recordCollectionId"))
    abstract fun toUpdateView(source: RecordEntity): RecordUpdateView
}
