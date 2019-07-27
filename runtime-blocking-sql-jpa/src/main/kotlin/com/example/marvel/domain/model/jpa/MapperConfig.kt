package com.example.marvel.domain.model.jpa

import org.mapstruct.CollectionMappingStrategy.ACCESSOR_ONLY
import org.mapstruct.InjectionStrategy.CONSTRUCTOR
import org.mapstruct.MapperConfig
import org.mapstruct.MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG
import org.mapstruct.NullValueCheckStrategy.ALWAYS
import org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
import org.mapstruct.ReportingPolicy.ERROR
import org.mapstruct.ReportingPolicy.WARN

@MapperConfig(
        componentModel                    = "jsr330",
        collectionMappingStrategy         = ACCESSOR_ONLY,
        injectionStrategy                 = CONSTRUCTOR,
        mappingInheritanceStrategy        = AUTO_INHERIT_ALL_FROM_CONFIG,
        nullValueCheckStrategy            = ALWAYS,
        nullValuePropertyMappingStrategy  = IGNORE,
        unmappedSourcePolicy              = WARN,
        unmappedTargetPolicy              = ERROR)
interface MapperConfig
