package com.example.marvel.domain.recordcollection

import com.example.marvel.api.RecordCollectionView
import io.micronaut.core.annotation.Introspected
import java.time.YearMonth

@Introspected
data class RecordCollectionListingView(
    //@formatter:off
    override val yearMonth                    : YearMonth,
    override val projectId                    : String,
    override val employeeId                   : Long
    //@formatter:on
) : RecordCollectionView
