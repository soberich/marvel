package com.example.marvel.domain.record

import com.example.marvel.api.RecordCommand.RecordCreateCommand
import com.example.marvel.api.RecordCommand.RecordUpdateCommand
import com.example.marvel.domain.GenericMapper
import com.example.marvel.domain.tmp.RecordMapperImpl
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RecordMapperImplTest {
    private lateinit var cut: RecordMapperImpl

    @MockK
    lateinit var genericMapper: GenericMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        cut = RecordMapperImpl(genericMapper)
    }

    @Test
    fun `Given _ when toEntity(RecordCreateCommand) then _`() {
        // Given
        val source = mockk<RecordCreateCommand>()

        // When
        val actualValue = cut.toEntity(source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toEntity(RecordCreateCommand) then throws exception`() {
        // Given
        val source = mockk<RecordCreateCommand>()
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
    fun `Given _ when toEntity(RecordUpdateCommand) then _`() {
        // Given
        val source = mockk<RecordUpdateCommand>()

        // When
        val actualValue = cut.toEntity(source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toEntity(RecordUpdateCommand) then throws exception`() {
        // Given
        val source = mockk<RecordUpdateCommand>()
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
    fun `Given _ when toCreateView then _`() {
        // Given
        val source = mockk<RecordEntity>()

        // When
        val actualValue = cut.toCreateView(source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toCreateView then throws exception`() {
        // Given
        val source = mockk<RecordEntity>()
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
        val source = mockk<RecordEntity>()

        // When
        val actualValue = cut.toUpdateView(source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toUpdateView then throws exception`() {
        // Given
        val source = mockk<RecordEntity>()
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
