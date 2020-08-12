package com.example.marvel.domain.recordcollection

import com.example.marvel.api.RecordCollectionCommand.RecordCollectionCreateCommand
import com.example.marvel.api.RecordCollectionCommand.RecordCollectionUpdateCommand
import com.example.marvel.domain.GenericMapper
import com.example.marvel.domain.record.RecordMapper
import com.example.marvel.domain.tmp.RecordCollectionMapperImpl
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import java.io.Serializable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RecordCollectionMapperImplTest {
    private lateinit var cut: RecordCollectionMapperImpl

    @MockK
    lateinit var recordMapper: RecordMapper

    @MockK
    lateinit var genericMapper: GenericMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        cut = RecordCollectionMapperImpl(recordMapper, genericMapper)
    }

    @Test
    fun `Given _ when toEntity(RecordCollectionCreateCommand) then _`() {
        // Given
        val source = mockk<RecordCollectionCreateCommand>()

        // When
        val actualValue = cut.toEntity(source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toEntity(RecordCollectionCreateCommand) then throws exception`() {
        // Given
        val source = mockk<RecordCollectionCreateCommand>()
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
    fun `Given _ when toEntity(Serializable, RecordCollectionUpdateCommand) then _`() {
        // Given
        val id = mockk<Serializable>()
        val source = mockk<RecordCollectionUpdateCommand>()

        // When
        val actualValue = cut.toEntity(id, source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toEntity(Serializable, RecordCollectionUpdateCommand) then throws exception`() {
        // Given
        val id = mockk<Serializable>()
        val source = mockk<RecordCollectionUpdateCommand>()
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
        val source = mockk<RecordCollectionEntity>()

        // When
        val actualValue = cut.toCreateView(source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toCreateView then throws exception`() {
        // Given
        val source = mockk<RecordCollectionEntity>()
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
        val source = mockk<RecordCollectionEntity>()

        // When
        val actualValue = cut.toUpdateView(source)

        // Then
        TODO("Define assertions")
    }

    @Test
    fun `Given _ when toUpdateView then throws exception`() {
        // Given
        val source = mockk<RecordCollectionEntity>()
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
