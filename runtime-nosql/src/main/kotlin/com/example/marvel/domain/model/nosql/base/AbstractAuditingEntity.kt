package com.example.marvel.domain.model.nosql.base

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.time.Instant

abstract class AbstractAuditingEntity<T : Serializable>(
    override
    val id                  : T
) : IdentityOf<T>() {

    @JsonIgnore
    val createdBy           : String? = null

    @JsonIgnore
    val createdDate         : Instant = Instant.now()

    @JsonIgnore
    val lastModifiedBy      : String? = null

    @JsonIgnore
    val lastModifiedDate    : Instant = Instant.now()
}
