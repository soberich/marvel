package com.example.marvel.domain.recordcollection

import com.example.marvel.domain.base.SimpleGeneratedIdentityOfLong
import com.example.marvel.domain.employee.EmployeeEntity
import com.example.marvel.domain.project.ProjectEntity
import com.example.marvel.domain.record.RecordEntity
import org.hibernate.annotations.Columns
import org.hibernate.annotations.Type
import java.time.YearMonth
import javax.persistence.*
import javax.persistence.AccessType.PROPERTY
import javax.persistence.CascadeType.ALL
import javax.persistence.FetchType.LAZY
import kotlin.properties.Delegates

@Entity
@Cacheable
@Access(PROPERTY)
//@DynamicUpdate
//@SelectBeforeUpdate
//@OptimisticLocking(type = DIRTY)
class RecordCollectionEntity : SimpleGeneratedIdentityOfLong() {
    //@formatter:off
    @get:
    [Type(type = "yearmonth-composite")
    Columns(columns = [
        Column(name = "year", nullable = false, updatable = false),
        Column(name = "month", nullable = false, updatable = false)])]
    var yearMonth                             : YearMonth by Delegates.notNull()
    @get:
    [ManyToOne(optional = false, fetch = LAZY)
    JoinColumn(updatable =false)]
    var project                               : ProjectEntity by Delegates.notNull()
    @get:
    [ManyToOne(optional = false, fetch = LAZY)
    JoinColumn(updatable = false)]
    var employee                              : EmployeeEntity by Delegates.notNull()

    @get:
    [OrderBy("date DESC")
    OneToMany(mappedBy = "report", cascade = [ALL], orphanRemoval = true)]
    var records                               : MutableSet<RecordEntity> = linkedSetOf()
    //@formatter:on
}
