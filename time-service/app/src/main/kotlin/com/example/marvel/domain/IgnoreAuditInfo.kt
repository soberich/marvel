package com.example.marvel.domain

import org.mapstruct.Mapping
import org.mapstruct.Mappings
import kotlin.annotation.AnnotationRetention.BINARY

@Mappings(
    Mapping(ignore = true, target = "createdBy"),
    Mapping(ignore = true, target = "createdDate"),
    Mapping(ignore = true, target = "lastModifiedBy"),
    Mapping(ignore = true, target = "lastModifiedDate"))
@Retention(BINARY)
annotation class IgnoreAuditInfo
