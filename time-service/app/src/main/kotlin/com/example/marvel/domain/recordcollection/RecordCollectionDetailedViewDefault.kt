package com.example.marvel.domain.recordcollection

import com.example.marvel.api.RecordCollectionDetailedView
import com.example.marvel.api.RecordDetailedView
import java.time.Month
import java.time.Year

data class RecordCollectionDetailedViewDefault(
    //@formatter:off
    override val year                         : Year,
    override val month                        : Month,
    override val projectId                    : String,
    override val employeeId                   : Long,
    override val records                      : List<RecordDetailedView>
    //@formatter:on
) : RecordCollectionDetailedView
