package com.example.marvel.web.rest.jakarta

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Pojo {
    @Id
    @GeneratedValue
    var id: Long? = null

    var name: String? = null
}
