package com.example.marvel.domain.recordcollection

import com.example.marvel.api.RecordCollectionView
import java.time.Month
import java.time.Year

data class RecordCollectionListingView(
    //@formatter:off
    override val year                         : Year,
    override val month                        : Month,
    override val projectId                    : String,
    override val employeeId                   : Long
    //@formatter:on
) : RecordCollectionView
