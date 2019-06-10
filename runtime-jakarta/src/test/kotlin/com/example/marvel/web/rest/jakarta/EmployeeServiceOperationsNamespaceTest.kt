package com.example.marvel.web.rest.jakarta

import org.junit.jupiter.api.Assertions.assertNotNull
import javax.inject.Inject

/**
 * FIXME: Tests don't work with Kotlin setup.
 */
//@QuarkusTest
class EmployeeServiceOperationsNamespaceTest {

    @Inject
    lateinit var resource: EmployeeOrchestrationResource

    //@Test
    fun getAnyUserDemo() {
        assertNotNull(resource)

    }
}
