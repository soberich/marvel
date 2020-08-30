package com.example.marvel.domain.record

import com.blazebit.persistence.view.EntityView
import com.blazebit.persistence.view.IdMapping
import com.blazebit.persistence.view.Mapping
import com.example.marvel.api.RecordDetailedView

@EntityView(RecordEntity::class)
interface RecordDetailedViewDefault : RecordDetailedView {
    @get:IdMapping("this")
    val id                           : RecordIdView
    @get:Mapping("report.id")
    override val recordCollectionId  : Long
}
