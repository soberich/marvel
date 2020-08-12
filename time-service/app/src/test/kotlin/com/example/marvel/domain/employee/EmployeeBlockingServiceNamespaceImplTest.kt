package com.example.marvel.domain.employee

import com.example.marvel.api.EmployeeCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionUpdateCommand
import com.example.marvel.domain.recordcollection.RecordCollectionMapper
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EmployeeBlockingServiceNamespaceImplTest {
    private lateinit var cut: EmployeeBlockingServiceNamespaceImpl

    @MockK
    lateinit var empMapper: EmployeeMapper

    @MockK
    lateinit var recColMapper: RecordCollectionMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        cut = EmployeeBlockingServiceNamespaceImpl(empMapper, recColMapper)
    }

    @Test
    fun `Given _ when streamEmployees then _`() {
        // Given

        // When
        val actualValue = cut.streamEmployees()

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when streamEmployees then throws exception`() {
        // Given
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.streamEmployees()
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }

    @Test
    fun `Given _ when filterEmployees then _`() {
        // Given
        val filter = "filter"

        // When
        val actualValue = cut.filterEmployees(filter)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when filterEmployees then throws exception`() {
        // Given
        val filter = "filter"
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.filterEmployees(filter)
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }

    @Test
    fun `Given _ when createEmployee then _`() {
        // Given
        val employee = mockk<EmployeeCommand.EmployeeCreateCommand>()

        // When
        val actualValue = cut.createEmployee(employee)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when createEmployee then throws exception`() {
        // Given
        val employee = mockk<EmployeeCommand.EmployeeCreateCommand>()
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
        val employee = mockk<EmployeeCommand.EmployeeUpdateCommand>()

        // When
        val actualValue = cut.updateEmployee(employee)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when updateEmployee then throws exception`() {
        // Given
        val employee = mockk<EmployeeCommand.EmployeeUpdateCommand>()
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
    fun `Given _ when createWholePeriod then _`() {
        // Given
        val records = mockk<RecordCollectionCreateCommand>()

        // When
        val actualValue = cut.createWholePeriod(records)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when createWholePeriod then throws exception`() {
        // Given
        val records = mockk<RecordCollectionCreateCommand>()
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.createWholePeriod(records)
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }

    @Test
    fun `Given _ when updateWholePeriod then _`() {
        // Given
        val records = mockk<RecordCollectionUpdateCommand>()

        // When
        val actualValue = cut.updateWholePeriod(records)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when updateWholePeriod then throws exception`() {
        // Given
        val records = mockk<RecordCollectionUpdateCommand>()
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.updateWholePeriod(records)
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }

    @Test
    fun `Given _ when getAnyUserDemo then _`() {
        // Given

        // When
        val actualValue = cut.getAnyUserDemo()

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when getAnyUserDemo then throws exception`() {
        // Given
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.getAnyUserDemo()
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }
}
