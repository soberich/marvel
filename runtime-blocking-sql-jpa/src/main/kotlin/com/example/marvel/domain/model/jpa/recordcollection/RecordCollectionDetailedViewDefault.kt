package com.example.marvel.domain.model.jpa.recordcollection

import com.example.marvel.domain.model.api.record.RecordDetailedView
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDetailedView
import java.time.Month

data class RecordCollectionDetailedViewDefault(
    override val year                         : Int,
    override val month                        : Month,
    override val projectId                    : String,
    override val employeeId                   : Long,
    override val records                      : List<RecordDetailedView>
) : RecordCollectionDetailedView
