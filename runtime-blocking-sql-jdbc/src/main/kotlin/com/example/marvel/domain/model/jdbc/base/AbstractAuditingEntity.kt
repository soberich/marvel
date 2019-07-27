package com.example.marvel.domain.model.jdbc.base

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.annotation.WhenCreated
import io.ebean.annotation.WhenModified
import io.ebean.annotation.WhoCreated
import io.ebean.annotation.WhoModified
import java.io.Serializable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractAuditingEntity<T : Serializable>(
    @Id
    @GeneratedValue
    @Column(updateble = false)
    override
    val id                   : T
) : IdentityOf<T>() {

    @WhoCreated
    @Column(nullable = false, updatable = false)
    @JsonIgnore
    val createdBy           : String? = null

    @WhenCreated
    @Column(nullable = false, updatable = false)
    @JsonIgnore
    val createdDate         : Instant = Instant.now()

    @WhoModified
    @Column(nullable = false, length = 50)
    @JsonIgnore
    val lastModifiedBy      : String? = null

    @WhenModified
    @Column(nullable = false)
    @JsonIgnore
    val lastModifiedDate    : Instant = Instant.now()
}
