package com.example.marvel.domain.model.jdbc.base

import java.io.Serializable

/**
 * A minimal valuable identifiable
 */
interface IdentifiableOf<T : Serializable> {
    val id: T
}
