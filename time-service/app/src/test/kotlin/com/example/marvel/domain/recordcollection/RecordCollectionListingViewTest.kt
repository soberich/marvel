package com.example.marvel.domain.recordcollection

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import java.time.YearMonth
import org.junit.Before

class RecordCollectionListingViewTest {
    private lateinit var cut: RecordCollectionListingView

    @MockK
    lateinit var yearMonth: YearMonth

    private val projectId = "projectId"

    private val employeeId = 0L

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        cut = RecordCollectionListingView(yearMonth, projectId, employeeId)
    }

}
