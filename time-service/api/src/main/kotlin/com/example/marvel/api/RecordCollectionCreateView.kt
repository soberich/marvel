package com.example.marvel.api

import com.example.marvel.api.RecordCollectionDetailedView
import com.example.marvel.api.RecordCreateView
import org.immutables.value.Value

@Value.Immutable
interface RecordCollectionCreateView : RecordCollectionDetailedView {
    override val records: List<RecordCreateView>
}
