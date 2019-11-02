package versioning

/**
 * To use versions in `plugin {}` block it needs to be in Kotlin not Java
 */
object Platforms {
    object Versions {
        const val BLAZE_JPA    = "1.3.2"
        const val COROUTINES   = "1.3.2"
        const val JACKSON      = "2.9.10"
        const val QUARKUS      = "0.26.1"
        const val REACTOR      = "Dysprosium-SR1"
        const val RESTEASY     = "4.1.1.Final"
        const val SPRING_BOOT  = "2.2.0.RELEASE"
        const val STATEMACHINE = "2.1.3.RELEASE"
    }
}
