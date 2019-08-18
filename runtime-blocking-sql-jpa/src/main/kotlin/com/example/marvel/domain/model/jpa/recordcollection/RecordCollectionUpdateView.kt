package com.example.marvel.domain.model.jpa.recordcollection

import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDetailedView
import com.example.marvel.domain.model.jpa.record.RecordUpdateView
import org.immutables.value.Value

@Value.Immutable
interface RecordCollectionUpdateView : RecordCollectionDetailedView {
    override val records: List<RecordUpdateView>
}
