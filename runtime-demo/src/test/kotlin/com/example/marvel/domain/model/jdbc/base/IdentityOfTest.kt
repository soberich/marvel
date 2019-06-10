package com.example.marvel.domain.model.jdbc.base

import com.example.marvel.domain.model.jdbc.base.IdentityOfTest.IdentityOfTest.equalsVerifier
import org.assertj.core.api.Assertions
import org.junit.Test

//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
//import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
//import org.springframework.format.support.DefaultFormattingConversionService
//import org.springframework.format.support.FormattingConversionService
//import org.springframework.http.MediaType

//import org.assertj.core.api.Assertions.assertThat

/**
 * Utility class
 */
class IdentityOfTest {

    @Test
    fun testEquals(): Unit {
        equalsVerifier(IdentityOf::class.java)
    }

    object IdentityOfTest {




        fun <T> equalsVerifier(clazz: Class<T>) {
            val domainObject1 = clazz.getConstructor().newInstance()
            Assertions.assertThat(domainObject1.toString()).isNotNull()
            Assertions.assertThat(domainObject1).isEqualTo(domainObject1)
            Assertions.assertThat(domainObject1.hashCode()).isEqualTo(domainObject1.hashCode())
            // Test with an instance of another class
            val testOtherObject = Any()
            Assertions.assertThat(domainObject1).isNotEqualTo(testOtherObject)
            Assertions.assertThat(domainObject1).isNotEqualTo(null)
            // Test with an instance of the same class
            val domainObject2 = clazz.getConstructor().newInstance()
            Assertions.assertThat(domainObject1).isNotEqualTo(domainObject2)
            // HashCodes are equals because the objects are not persisted yet
            Assertions.assertThat(domainObject1.hashCode()).isEqualTo(domainObject2.hashCode())
        }


    }

}
