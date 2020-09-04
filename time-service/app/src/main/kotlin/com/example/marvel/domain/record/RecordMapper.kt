package com.example.marvel.domain.record

import com.example.marvel.api.*
import com.example.marvel.domain.IgnoreAuditInfo
import com.example.marvel.domain.MapperConfig
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import kotlin.annotation.AnnotationRetention.BINARY

@Mapper(config = MapperConfig::class)
abstract class RecordMapper {

    @Mappings(
        Mapping(ignore = true                , target = "version"),
        Mapping(source = "recordCollectionId", target = "report"))
    @RecordToEntity
    abstract fun toEntity(source: RecordCreateCommand): RecordEntity

    @Mapping(ignore = true, target = "report")
    @RecordToEntity
    abstract fun toEntity(source: RecordUpdateCommand): RecordEntity

    @RecordToDto
    abstract fun toCreateView(source: RecordEntity): RecordCreateView

    @RecordToDto
    abstract fun toUpdateView(source: RecordEntity): RecordUpdateView

    @Mappings(
        Mapping(ignore = true, target = "id"))
    @IgnoreAuditInfo
    @Retention(BINARY)
    annotation class RecordToEntity

    @Mapping(source = "report.id", target = "recordCollectionId")
    @Retention(BINARY)
    annotation class RecordToDto
}
