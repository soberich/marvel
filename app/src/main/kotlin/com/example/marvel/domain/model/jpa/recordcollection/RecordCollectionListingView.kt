package com.example.marvel.domain.model.jpa.recordcollection

import java.time.Month

data class RecordCollectionListingView(
    override val year                         : Int,
    override val month                        : Month,
    override val projectId                    : String,
    override val employeeId                   : Long
) : RecordCollectionView
