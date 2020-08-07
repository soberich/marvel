package com.example.marvel.api

import java.time.Month
import java.time.Year

interface RecordCollectionDetailedView {
    //@formatter:off
    val year                         : Year
    val month                        : Month
    val projectId                    : String
    val employeeId                   : Long
    val records                      : List<@JvmWildcard RecordDetailedView>
    //@formatter:on
}
