package com.example.marvel.domain.base

import java.io.Serializable

/**
 * A minimal valuable identifiable.
 */
interface IdentifiableOf<out T : Serializable> {
    /**
     * id value
     */
    val id: T?
}
