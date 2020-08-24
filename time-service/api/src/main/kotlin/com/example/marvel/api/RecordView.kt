package com.example.marvel.api

import java.time.Duration
import java.time.LocalDate

interface RecordView {
    //@formatter:off
    val version             : Int
    val date                : LocalDate
    val type                : RecordType
    val hoursSubmitted      : Duration
    val desc                : String?
    val recordCollectionId  : Long
    //@formatter:on
}
