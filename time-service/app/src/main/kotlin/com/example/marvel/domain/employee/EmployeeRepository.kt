package com.example.marvel.domain.employee

//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.data.jpa.repository.Query
import com.example.marvel.domain.record.RecordEntity
import com.example.marvel.domain.record.RecordListingView
import io.micronaut.data.annotation.Query
//import io.micronaut.data.annotation.Query
//import io.micronaut.data.annotation.Repository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.Month

@Repository
interface EmployeeRepository : CrudRepository<RecordEntity, Long>/*, PagingAndSortingRepository<RecordEntity, Long>*/ {

    @Query("""
        SELECT p.date, p.type, p.hoursSubmitted, p.desc, p.report.id
        FROM   RecordEntity p
        JOIN   p.report c
        WHERE  c.id    = :id
          AND  c.year  = :year
          AND  c.month = :month""", nativeQuery = true)
//    @Join(value = "report", type = Join.Type.FETCH)
    open fun listForPeriod(
        /*@Param("id")*/ id: Long,
        /*@Param("year")*/ year: Int,
        /*@Param("month")*/ month: Month): List<RecordListingView>
}
