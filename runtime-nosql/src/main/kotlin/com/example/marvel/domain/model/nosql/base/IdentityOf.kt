package com.example.marvel.domain.model.nosql.base

import java.io.Serializable
import java.util.*

/**
 * IdentityOf holder for and identity type
 */
abstract class IdentityOf<T : Serializable>: IdentifiableOf<T> {

    @kotlin.jvm.Transient
    protected var optimisticVersion = 0

    final override fun equals(other: Any?) = when {
        this === other                                          -> true
        other !is IdentityOf<*>                                 -> false
        !other::class.java.isAssignableFrom(this::class.java) -> false
        !(other.canEqual(this))                                 -> false
        else                                                    -> id  == other.id
    }

    /**
     *       final override fun equals(other: Any?) = ...
     *       final override fun hashCode() = ...
     *
     * Such overrides allow us write all properties inside primary ctor
     * in data classes.
     */

    private fun canEqual(other: Any) = other::class.java.isAssignableFrom(this::class.java)

    final override fun hashCode() = Objects.hashCode(id)

    final override fun toString() = "Entity of ${this::class.java.name} with id: $id"
}
