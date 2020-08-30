package com.example.marvel.domain.recordcollection

import com.blazebit.persistence.view.EntityView
import com.blazebit.persistence.view.IdMapping
import com.blazebit.persistence.view.Mapping
import com.example.marvel.api.RecordCollectionView

@EntityView(RecordCollectionEntity::class)
interface RecordCollectionListingView : RecordCollectionView {
    @get:IdMapping
    val id: Long
    @get:Mapping("project.id")
    override val projectId: String
    @get:Mapping("employee.id")
    override val employeeId: Long
}
