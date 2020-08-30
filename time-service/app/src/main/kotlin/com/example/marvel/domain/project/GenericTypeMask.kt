package com.example.marvel.domain.project

import com.example.marvel.domain.base.AbstractAuditingEntity
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class GenericTypeMask : AbstractAuditingEntity<String>()
