package com.example.marvel.domain.base

import java.io.Serializable
import javax.persistence.Access
import javax.persistence.AccessType.FIELD
import javax.persistence.AccessType.PROPERTY
import javax.persistence.MappedSuperclass
import javax.persistence.Version

/**
 * IdentityOf holder for any (NON-generated) identity type.
 */
@MappedSuperclass
@Access(PROPERTY)
abstract class BusinessKeyIdentityOf<out T : Serializable> : IdentifiableOf<T> {

    /**
     * Allows creating transient entity instances with default ctor and set them to `@ManyToOne` owning side
     * considering they doesn't have any of
     * [javax.persistence.CascadeType.PERSIST]
     * [javax.persistence.CascadeType.MERGE]
     * [org.hibernate.annotations.CascadeType.SAVE_UPDATE]
     */
    @field:
    [Version
    Access(FIELD)]
    private var optimisticVersion = 0

    /**
     *       final override fun equals(other: Any?) = ...
     *       final override fun hashCode() = ...
     *
     * Such overrides would've allow us write all properties inside primary ctor
     * in data classes
     * FIXME: Bytecode enhancers intercepts equals too, unfortunately
     *   SecurityManager screams final method overridden.
     *   So, for now we may leave these open and override them in each Entity
     */
    override fun equals(other: Any?): Boolean = when {
        this === other                     -> true
        other !is BusinessKeyIdentityOf<*> -> false
        !other.canEqual(this)              -> false
        else                               -> id == other.id
    }

    /**
     * This implementation is default and preferred to be overridden
     */
    protected open fun canEqual(other: Any): Boolean = this::class.java.isAssignableFrom(other::class.java)

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = "Entity of ${this::class.simpleName} with id: $id"
}
