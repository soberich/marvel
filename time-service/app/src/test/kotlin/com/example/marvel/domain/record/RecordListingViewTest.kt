package com.example.marvel.domain.record

import com.example.marvel.api.RecordType
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import java.time.Duration
import java.time.LocalDate
import org.junit.Before

class RecordListingViewTest {
    private lateinit var cut: RecordListingView

    @MockK
    lateinit var date: LocalDate

    @MockK
    lateinit var type: RecordType

    @MockK
    lateinit var hoursSubmitted: Duration

    private val desc = "desc"

    private val recordCollectionId = 0L

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
//        cut = RecordListingView(date, type, hoursSubmitted, desc, recordCollectionId)
    }

}
