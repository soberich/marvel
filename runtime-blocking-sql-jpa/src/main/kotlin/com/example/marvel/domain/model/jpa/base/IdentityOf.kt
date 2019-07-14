package com.example.marvel.domain.model.jpa.base

import java.io.Serializable
import java.util.Objects
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.MappedSuperclass
import javax.persistence.Version


/**
 * IdentityOf holder for identity type
 */

@MappedSuperclass
@Access(PROPERTY)
abstract class IdentityOf<T : Serializable>: IdentifiableOf<T> {

    @get:Version
    protected var optimisticVersion = 0

    /**
     *       final override fun equals(other: Any?) = ...
     *       final override fun hashCode() = ...
     *
     * Such overrides allow us write all properties inside primary ctor
     * in data classes
     * FIXME: Ebean intercepts equals too, unfortunately
     *   SecurityManager screams final method overridden.
     *   So, for now we may leave these open and override them in each Entity
     */
    override fun equals(other: Any?) = when {
        this === other                                        -> true
        other !is IdentityOf<*>                               -> false
        !other::class.java.isAssignableFrom(this::class.java) -> false
        !other.canEqual(this)                                 -> false
        else                                                  -> id  == other.id
    }

    protected open fun canEqual(other: Any) = other::class.java.isAssignableFrom(this::class.java)

    override fun hashCode() = Objects.hashCode(id)

    override fun toString() = "Entity of ${this::class.java.name} with id: $id"
}
