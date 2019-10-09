package com.example.marvel.domain.model.jpa.recordcollection

import com.example.marvel.domain.model.jpa.base.SimpleGeneratedIdentityOfLong
import com.example.marvel.domain.model.jpa.employee.EmployeeEntity
import com.example.marvel.domain.model.jpa.project.ProjectEntity
import com.example.marvel.domain.model.jpa.record.RecordEntity
import java.time.Month
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
class RecordCollectionEntity : SimpleGeneratedIdentityOfLong() {
    @get:
    [Column(nullable = false, updatable = false)]
    var year                                  : Int = 0
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
    JoinColumn(updatable =false)]
    lateinit var employee                     : EmployeeEntity

    @get:
    [OneToMany(mappedBy = "report", cascade = [ALL], orphanRemoval = true)]
    lateinit var records                      : List<RecordEntity>
}