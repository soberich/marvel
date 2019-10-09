package com.example.marvel.domain.model.jpa.employee

import com.example.marvel.domain.model.jpa.record.RecordListingView
import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionEntity_
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Month

@Repository
interface EmployeeRepository : JpaRepository<EmployeeEntity, Long> {

    @Query("""
        SELECT NEW com.example.marvel.domain.model.jpa.record.RecordListingView(p.date, p.type, p.hoursSubmitted, p.desc, p.report.id)
        FROM   RecordEntity p
        JOIN   p.report c
        WHERE  c.id    = :${RecordCollectionEntity_.ID}
          AND  c.year  = :${RecordCollectionEntity_.YEAR}
          AND  c.month = :${RecordCollectionEntity_.MONTH}""")
    fun listForPeriod(
        @Param(RecordCollectionEntity_.ID) id: Long,
        @Param(RecordCollectionEntity_.YEAR) year: Int,
        @Param(RecordCollectionEntity_.MONTH) month: Month): List<RecordListingView>


}
