package com.example.marvel.runtime

import io.ktor.features.*
import javax.validation.ConstraintViolation
import javax.validation.Validator

@Throws(BadRequestException::class)
fun <T : Any> T.validate(validator: Validator) {
    validator.validate(this)
        .takeIf(Collection<ConstraintViolation<T>?>::isNotEmpty)
        ?.run { throw BadRequestException(first().messageWithFieldName()) }
}

fun <T : Any> ConstraintViolation<T>.messageWithFieldName() = "${this.propertyPath} ${this.message}"
