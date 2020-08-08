package com.example.marvel.api

import java.time.YearMonth

interface RecordCollectionDetailedView {
    //@formatter:off
    val yearMonth                    : YearMonth
    val projectId                    : String
    val employeeId                   : Long
    val records                      : List<@JvmWildcard RecordDetailedView>
    //@formatter:on
}
