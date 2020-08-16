package versioning

/**
 * To use versions in `plugin {}` block it needs to be in Kotlin not Java
 */
object Platforms {
    object Versions {
        //@formatter:off
        const val ARROW          = "0.11.0-SNAPSHOT"
        const val BLAZE_JPA      = "1.4.1"
        const val COROUTINES     = "1.3.9"
        const val GUAVA          = "29.+"
        const val JACKSON        = "2.11.2"//"2.10.3"
        const val QUARKUS        = "1.7.0.Final"
        const val MICRONAUT      = "2.0.1"
        const val MICRONAUT_DATA = "+"
        const val REACTOR        = "Dysprosium-SR5"
        const val RESTEASY       = "4.1.1.Final"
        const val SPRING_BOOT    = "2.3.2.RELEASE"
        const val STATEMACHINE   = "3.0.0 M2"

        const val JUNIT5         = "5.7.0-M1"
        const val SPOCK          = "2.0-M2-groovy-3.0"
        //@formatter:on
    }
}
