package com.example.marvel.domain.recordcollection

import com.example.marvel.api.RecordCollectionDetailedView
import com.example.marvel.api.RecordDetailedView
import java.time.YearMonth

data class RecordCollectionDetailedViewDefault(
    //@formatter:off
    override val yearMonth                    : YearMonth,
    override val projectId                    : String,
    override val employeeId                   : Long,
    override val records                      : List<RecordDetailedView>
    //@formatter:on
) : RecordCollectionDetailedView
