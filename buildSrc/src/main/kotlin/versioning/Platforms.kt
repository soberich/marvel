package versioning

/**
 * To use versions in `plugin {}` block it needs to be in Kotlin not Java
 */
object Platforms {
    object Versions {
        //@formatter:off
        const val ARROW          = "0.11.0-SNAPSHOT"
        const val BLAZE_JPA      = "1.6.0-Alpha2"
        const val COROUTINES     = "1.4.3"
        const val GUAVA          = "30.+"
        const val IMMUTABLES     = "2.8.9-SNAPSHOT"
        const val JACKSON        = "2.11.2"//"2.10.3"
        const val JAXB           = "3.0.0-M4"
        const val KTOR           = "1.4.0"
        const val MICRONAUT      = "1.3.7"
        const val MICRONAUT_DATA = "+"
        const val QUARKUS        = "1.7.1.Final"
        const val RESTEASY       = "4.1.1.Final"

        const val JUNIT5         = "5.7.0-M1"
        const val SPOCK          = "2.0-M2-groovy-3.0"
        //@formatter:on
    }
}
