package com.example.marvel.domain.model.jpa.base

import java.io.Serializable
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.MappedSuperclass

/**
 * IdentityOf holder for GENERATED identity type.
 */
@MappedSuperclass
@Access(PROPERTY)
abstract class JpaStateTransitionAwareIdentityOf<T : Serializable> : BusinessKeyIdentityOf<T>() {

    @Volatile
    private var bucketDisperser: Any? = null
        get() {
            if (field != null || field == null && id == null) {
                if (field == null) {
                    synchronized(this) {
                        if (field == null) {
                            field = Any()
                        }
                    }
                }
                return field
            }
            return id
        }

    override fun equals(other: Any?) = when {
        this === other                                 -> true
        other !is JpaStateTransitionAwareIdentityOf<*> -> false
        !other.canEqual(this)                          -> false
        else                                           -> id  == other.id
    }

    override fun hashCode() = bucketDisperser.hashCode()
}
