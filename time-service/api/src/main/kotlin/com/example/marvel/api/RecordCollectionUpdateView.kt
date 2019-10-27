package com.example.marvel.api

import org.immutables.value.Value

@Value.Immutable
interface RecordCollectionUpdateView : RecordCollectionDetailedView {
    override val records: List<RecordUpdateView>
}
