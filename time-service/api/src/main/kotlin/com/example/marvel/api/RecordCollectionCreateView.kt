package com.example.marvel.api

import org.immutables.value.Value

@Value.Immutable
interface RecordCollectionCreateView : RecordCollectionDetailedView {
    override val records: List<RecordCreateView>
}
