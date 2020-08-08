package com.example.marvel.api

import java.time.YearMonth

interface RecordCollectionView {
    //@formatter:off
    val yearMonth                    : YearMonth
    val projectId                    : String
    val employeeId                   : Long
    //@formatter:on
}
