package com.example.marvel.domain.recordcollection

import com.blazebit.persistence.view.EntityView
import com.blazebit.persistence.view.IdMapping
import com.blazebit.persistence.view.Mapping
import com.example.marvel.api.RecordCollectionDetailedView
import com.example.marvel.domain.record.RecordDetailedViewDefault

@EntityView(RecordCollectionEntity::class)
interface RecordCollectionDetailedViewDefault : RecordCollectionDetailedView {
    @get:IdMapping
    override val id: Long
    @get:Mapping("project.id")
    override val projectId: String
    @get:Mapping("employee.id")
    override val employeeId: Long
    override val records: List<RecordDetailedViewDefault>
}
