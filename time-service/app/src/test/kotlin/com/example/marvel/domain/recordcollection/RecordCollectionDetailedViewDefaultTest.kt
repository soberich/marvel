package com.example.marvel.domain.recordcollection

import com.example.marvel.api.RecordDetailedView
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import java.time.YearMonth
import org.junit.Before

class RecordCollectionDetailedViewDefaultTest {
    private lateinit var cut: RecordCollectionDetailedViewDefault

    private val id = 0L

    private val version = 1

    @MockK
    lateinit var yearMonth: YearMonth

    private val projectId = "projectId"

    private val employeeId = 0L

    private val records = listOf<RecordDetailedView>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
//        cut = RecordCollectionDetailedViewDefault(id, version, yearMonth, projectId, employeeId, records)
    }

}
