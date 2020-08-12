package com.example.marvel.domain.recordcollection

import com.example.marvel.api.RecordCollectionView
import java.time.YearMonth

data class RecordCollectionListingView(
    //@formatter:off
    override val yearMonth                    : YearMonth,
    override val projectId                    : String,
    override val employeeId                   : Long
    //@formatter:on
) : RecordCollectionView
