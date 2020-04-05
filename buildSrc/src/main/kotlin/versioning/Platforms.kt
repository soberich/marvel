package versioning

/**
 * To use versions in `plugin {}` block it needs to be in Kotlin not Java
 */
object Platforms {
    object Versions {
        const val BLAZE_JPA    = "1.4.1"
        const val COROUTINES   = "1.3.5-1.4-M1"
        const val JACKSON      = "2.10.3"
        const val QUARKUS      = "1.3.0.Final"
        const val MICRONAUT    = "2.0.0.M2"
        const val REACTOR      = "Dysprosium-SR5"
        const val RESTEASY     = "4.1.1.Final"
        const val SPRING_BOOT  = "2.3.0.M3"
        const val STATEMACHINE = "3.0.0 M2"
    }
}
