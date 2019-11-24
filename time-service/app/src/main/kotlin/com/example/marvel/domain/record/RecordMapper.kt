package com.example.marvel.domain.record

import com.example.marvel.api.RecordCommand
import com.example.marvel.api.RecordCreateView
import com.example.marvel.api.RecordUpdateView
import com.example.marvel.domain.GenericMapper
import com.example.marvel.domain.MapperConfig
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
