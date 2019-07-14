package com.example.marvel.domain.model.jpa.base

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence.Id
import javax.persistence.MappedSuperclass


/**
 * IdentityOf holder for and identity type
 */

@MappedSuperclass
@Access(PROPERTY)
abstract class SimpleGeneratedIdentityOfLong: IdentityOf<Long>() {

    @get:Id
    @get:GenericGenerator(
            name = "optimized-sequence",
            strategy = "enhanced-sequence",
            parameters = [
                Parameter(name = "prefer_sequence_per_entity" , value = "true"),
                Parameter(name = "initial_value"              , value = "1"),
                Parameter(name = "increment_size"             , value = "25"),
                Parameter(name = "optimizer"                  , value = "pooled")])
    @get:GeneratedValue(strategy = SEQUENCE, generator = "optimized-sequence")
    @get:Column(updatable = false)
    override var id: Long? = 0L
}
