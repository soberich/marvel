package com.example.marvel.api

import java.time.Month

interface RecordCollectionDetailedView {
    val year                         : Int
    val month                        : Month
    val projectId                    : String
    val employeeId                   : Long
    val records                      : List<@JvmWildcard RecordDetailedView>
}
