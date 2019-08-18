package com.example.marvel.domain.model.jpa.recordcollection

import java.time.Month

interface RecordCollectionView {
    val year                         : Int
    val month                        : Month
    val projectId                    : String
    val employeeId                   : Long
}
