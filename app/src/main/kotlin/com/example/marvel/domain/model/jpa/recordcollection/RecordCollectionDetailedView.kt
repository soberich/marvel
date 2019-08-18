package com.example.marvel.domain.model.jpa.recordcollection

import com.example.marvel.domain.model.jpa.record.RecordDetailedView
import java.time.Month

interface RecordCollectionDetailedView {
    val year                         : Int
    val month                        : Month
    val projectId                    : String
    val employeeId                   : Long
    val records                      : List<@JvmWildcard RecordDetailedView>
}
