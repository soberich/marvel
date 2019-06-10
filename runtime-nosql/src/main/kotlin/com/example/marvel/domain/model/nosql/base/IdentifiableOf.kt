package com.example.marvel.domain.model.nosql.base

import java.io.Serializable

/**
 * A minimal valuable identifiable
 */
interface IdentifiableOf<T : Serializable> {
    val id: T
}
