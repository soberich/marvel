package com.example.marvel.domain.employee

import com.example.marvel.api.EmployeeCreateCommand
import com.example.marvel.api.EmployeeUpdateCommand
import com.example.marvel.domain.GenericMapper
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EmployeeMapperImplTest {
    private lateinit var cut: EmployeeMapperImpl

    @MockK
    lateinit var genericMapper: GenericMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        cut = EmployeeMapperImpl(genericMapper)
    }

    @Test
    fun `Given _ when toEntity(EmployeeCreateCommand) then _`() {
        // Given
        val source = mockk<EmployeeCreateCommand>()

        // When
        val actualValue = cut.toEntity(source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toEntity(EmployeeCreateCommand) then throws exception`() {
        // Given
        val source = mockk<EmployeeCreateCommand>()
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.toEntity(source)
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }

    @Test
    fun `Given _ when toEntity(Serializable, EmployeeUpdateCommand) then _`() {
        // Given
        val id = mockk<Long>()
        val source = mockk<EmployeeUpdateCommand>()

        // When
        val actualValue = cut.toEntity(id, source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toEntity(Serializable, EmployeeUpdateCommand) then throws exception`() {
        // Given
        val id = mockk<Long>()
        val source = mockk<EmployeeUpdateCommand>()
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.toEntity(id, source)
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }

    @Test
    fun `Given _ when toCreateView then _`() {
        // Given
        val source = mockk<EmployeeEntity>()

        // When
        val actualValue = cut.toCreateView(source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toCreateView then throws exception`() {
        // Given
        val source = mockk<EmployeeEntity>()
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.toCreateView(source)
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }

    @Test
    fun `Given _ when toUpdateView then _`() {
        // Given
        val source = mockk<EmployeeEntity>()

        // When
        val actualValue = cut.toUpdateView(source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toUpdateView then throws exception`() {
        // Given
        val source = mockk<EmployeeEntity>()
        val expectedException = Exception()
        lateinit var actualException: Exception

        // When
        try {
            cut.toUpdateView(source)
        } catch (exception: Exception) {
            actualException = exception
        }

        // Then
        assertEquals(expectedException, actualException)
    }
}
