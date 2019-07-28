package com.example.marvel.domain.model.jpa.base

import java.io.Serializable

/**
 * A minimal valuable identifiable.
 */
interface IdentifiableOf<T : Serializable> {
    val id: T?
}
