/**
 * Package contains entities, despite
 * Common recommendation NOT to use data classes for Entities,
 * but as for home grown project we try it.
 * TODO: Explore in impact of having lateinits in in Entities.
 *    lateinits allowing us to create that easy mappring with copy methods from `data classes`.
 *    @see com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionEntityKt#toRecordCollectionDto(RecordCollectionEntity)
 *    @see com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionEntityKt#toRecordCollectionDto(RecordCollectionEntity)
 */
package com.example.marvel.domain.model.jpa;

import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionEntity;
