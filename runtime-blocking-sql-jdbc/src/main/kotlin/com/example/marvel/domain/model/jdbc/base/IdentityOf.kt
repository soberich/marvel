package com.example.marvel.domain.model.jdbc.base

import io.ebean.Model
import java.io.Serializable
import java.util.*
import javax.persistence.MappedSuperclass
import javax.persistence.Version


/**
 * IdentityOf holder for and identity type
 */
@MappedSuperclass
abstract class IdentityOf<T : Serializable>: IdentifiableOf<T>, Model() {

    @Version
    @kotlin.jvm.Transient
    protected var optimisticVersion = 0

    override fun equals(other: Any?) = when {
        this === other                                        -> true
        other !is IdentityOf<*>                               -> false
        !other::class.java.isAssignableFrom(this::class.java) -> false
        !other.canEqual(this)                                 -> false
        else                                                  -> id  == other.id
    }

    /**
     *       final override fun equals(other: Any?) = ...
     *       final override fun hashCode() = ...
     *
     * Such overrides allow us write all properties inside primary ctor
     * in data classes
     * FIXME: Ebean intercepts equals too, unfortunately
     *   SecurityManager screams final method overridden.
     */

    protected open fun canEqual(other: Any) = other::class.java.isAssignableFrom(this::class.java)

    override fun hashCode() = Objects.hashCode(id)

    override fun toString() = "Entity of ${this::class.java.name} with id: $id"
}
