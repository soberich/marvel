package versioning

/**
 * To use versions in `plugin {}` block it needs to be in Kotlin not Java
 */
object Platforms {
    object Versions {
        const val BLAZE_JPA    = "1.3.2"
        const val COROUTINES   = "1.3.0-RC2"
        const val JACKSON      = "2.9.9.+"
        const val QUARKUS      = "0.23.2"
        const val REACTOR      = "Dysprosium-M2"
        const val RESTEASY     = "4.1.1.Final"
        const val SPRING_BOOT  = "2.2.0.M4"
        const val STATEMACHINE = "2.1.3.RELEASE"
    }
}

