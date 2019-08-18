package com.example.marvel.api

import java.time.Month

interface RecordCollectionView {
    val year                         : Int
    val month                        : Month
    val projectId                    : String
    val employeeId                   : Long
}
