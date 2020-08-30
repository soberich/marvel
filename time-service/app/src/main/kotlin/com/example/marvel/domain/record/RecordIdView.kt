package com.example.marvel.domain.record

import com.blazebit.persistence.view.EntityView
import com.blazebit.persistence.view.Mapping
import com.example.marvel.api.RecordType
import java.time.LocalDate

@EntityView(RecordEntity::class)
interface RecordIdView {
    //@formatter:off
    val date                : LocalDate
    val type                : RecordType
    @get:Mapping("report.id")
    val recordCollectionId  : Long
    //@formatter:on
}
