package com.example.marvel.domain.recordcollection

import com.example.marvel.domain.base.SimpleGeneratedIdentityOfLong
import com.example.marvel.domain.employee.EmployeeEntity
import com.example.marvel.domain.project.ProjectEntity
import com.example.marvel.domain.record.RecordEntity
import io.micronaut.core.annotation.Introspected
import java.time.Month
import java.time.Year
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.Cacheable
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.FetchType.LAZY
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
@Cacheable
@Access(PROPERTY)
//@DynamicUpdate
//@SelectBeforeUpdate
//@OptimisticLocking(type = DIRTY)
@Introspected
class RecordCollectionEntity : SimpleGeneratedIdentityOfLong() {
    //@formatter:off
    @get:
    [Column(nullable = false, updatable = false)]
    lateinit var year                         : Year
    @get:
    [Column(nullable = false, updatable = false)
    Enumerated(STRING)]
    lateinit var month                        : Month
    @get:
    [ManyToOne(optional = false, fetch = LAZY)
    JoinColumn(updatable =false)]
    lateinit var project                      : ProjectEntity
    @get:
    [ManyToOne(optional = false, fetch = LAZY)
    JoinColumn(updatable = false)]
    lateinit var employee                     : EmployeeEntity

    @get:
    [OneToMany(mappedBy = "report", cascade = [ALL], orphanRemoval = true)]
    var records                               : List<RecordEntity> = mutableListOf()
    //@formatter:on
}
