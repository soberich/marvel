package com.example.marvel.domain.project

import com.example.marvel.domain.base.BusinessKeyIdentityOf
import com.example.marvel.domain.recordcollection.RecordCollectionEntity
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
@Access(PROPERTY)
class ProjectEntity : BusinessKeyIdentityOf<String>() {
    @get:
    [Id
    Column(name = "name", length = 50)]
    override lateinit var id                  : String

//    @get:
//    [OneToMany(mappedBy = "project")]
//    lateinit var reports: List<RecordCollectionEntity>
}

