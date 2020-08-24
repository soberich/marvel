package com.example.marvel.domain.base

import java.io.Serializable

/**
 * A minimal valuable identifiable.
 */
/*fun */interface IdentifiableOf<out T : Serializable> {
    /**
     * id value
     */
    val id: T?
}
