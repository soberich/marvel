package com.example.marvel.domain.record

import com.example.marvel.domain.base.AbstractAuditingEntity
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class GenericTypeMask : AbstractAuditingEntity<RecordEntity.RecordId>()
