package com.example.marvel.domain.model.jpa.recordcollection

import com.example.marvel.api.RecordCollectionDetailedView
import com.example.marvel.api.RecordDetailedView
import java.time.Month

data class RecordCollectionDetailedViewDefault(
    override val year                         : Int,
    override val month                        : Month,
    override val projectId                    : String,
    override val employeeId                   : Long,
    override val records                      : List<RecordDetailedView>
) : RecordCollectionDetailedView
