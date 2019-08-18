package com.example.marvel.domain.model.jpa.recordcollection

import com.example.marvel.domain.model.jpa.record.RecordCreateView
import org.immutables.value.Value

@Value.Immutable
interface RecordCollectionCreateView : RecordCollectionDetailedView {
    override val records: List<RecordCreateView>
}
