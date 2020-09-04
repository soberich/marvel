package com.example.marvel.domain.employee

import com.example.marvel.api.EmployeeCreateCommand
import com.example.marvel.api.EmployeeUpdateCommand
import com.example.marvel.api.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionUpdateCommand
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EmployeeOrchestrationResourceTest {
    private lateinit var cut: EmployeeOrchestrationResource

    @MockK
    lateinit var employees: EmployeeBlockingServiceNamespaceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        cut = EmployeeOrchestrationResource(employees)
    }

    @Test
    fun `Given _ when getEmployees then _`() {
        // Given

        // When
        val actualValue = cut.getEmployees()

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when getEmployees then throws exception`() {
        // Given
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.getEmployees()
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }

    @Test
    fun `Given _ when createEmployee then _`() {
        // Given
        val employee = mockk<EmployeeCreateCommand>()

        // When
        val actualValue = cut.createEmployee(employee)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when createEmployee then throws exception`() {
        // Given
        val employee = mockk<EmployeeCreateCommand>()
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.createEmployee(employee)
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }

    @Test
    fun `Given _ when updateEmployee then _`() {
        // Given
        val employee = mockk<EmployeeUpdateCommand>()

        // When
        val actualValue = cut.updateEmployee(employee)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when updateEmployee then throws exception`() {
        // Given
        val employee = mockk<EmployeeUpdateCommand>()
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.updateEmployee(employee)
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }

    @Test
    fun `Given _ when saveWholePeriod then _`() {
        // Given
        val records = mockk<RecordCollectionCreateCommand>()

        // When
        val actualValue = cut.saveWholePeriod(records)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when saveWholePeriod then throws exception`() {
        // Given
        val records = mockk<RecordCollectionCreateCommand>()
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.saveWholePeriod(records)
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }

    @Test
    fun `Given _ when adjustWholePeriod then _`() {
        // Given
        val records = mockk<RecordCollectionUpdateCommand>()

        // When
        val actualValue = cut.adjustWholePeriod(records)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when adjustWholePeriod then throws exception`() {
        // Given
        val records = mockk<RecordCollectionUpdateCommand>()
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.adjustWholePeriod(records)
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }
}
