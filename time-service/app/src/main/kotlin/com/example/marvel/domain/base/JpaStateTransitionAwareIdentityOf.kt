package com.example.marvel.domain.base

import io.micronaut.core.annotation.Introspected
import java.io.Serializable
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.MappedSuperclass
import javax.persistence.Transient

/**
 * IdentityOf holder for GENERATED identity type.
 */
@MappedSuperclass
@Access(PROPERTY)
@Introspected
abstract class JpaStateTransitionAwareIdentityOf<out T : Serializable> : AbstractAuditingEntity<T>() {

    @Suppress("JpaAttributeTypeInspection")
    @get:
    [Transient]
    private val bucketDisperser by lazy { id ?: Any() }

    override fun equals(other: Any?): Boolean = when {
        this === other                                 -> true
        other !is JpaStateTransitionAwareIdentityOf<*> -> false
        !other.canEqual(this)                          -> false
        else                                           -> id == other.id
    }

    override fun hashCode(): Int = bucketDisperser.hashCode()
}
