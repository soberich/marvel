package com.example.marvel.domain.base

import java.io.Serializable

/**
 * A minimal valuable identifiable.
 */
interface IdentifiableOf<T : Serializable> {
    val id: T?
}
