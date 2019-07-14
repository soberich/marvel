@file:Suppress("NOTHING_TO_INLINE","OVERRIDE_BY_INLINE")

package com.example.marvel.domain.model.jpa.record

import com.example.marvel.domain.model.api.record.Record
import com.example.marvel.domain.model.api.record.RecordCreateCommand
import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.api.record.RecordModel
import com.example.marvel.domain.model.api.record.RecordUpdateCommand
import com.example.marvel.domain.model.enums.RecordType
import com.example.marvel.domain.model.jpa.base.IdentityOf
import com.example.marvel.domain.model.jpa.recordcollection.RecordCollectionEntity
import org.hibernate.annotations.Immutable
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import javax.json.bind.annotation.JsonbTransient
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.Cacheable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.FetchType.LAZY
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.NamedQuery
import javax.persistence.Transient

/**
 * This could be a just Tuple3
 * but we push to keep hexagonal: less imports (from arrow in this layer) => better.
 */
data class RecordId(var id: Long?, var date: LocalDate, var type: RecordType) : Serializable

/**
 * FIXME:
 *  N.B. The `date` is purposefully left first - to check the wrong way.
 */
@NamedQuery(name = "Record.findForPeriod", query = "SELECT p FROM RecordEntity p JOIN p.report c WHERE c.id = :id AND c.month = :month AND c.year = :year")

@Entity
@Immutable
@Cacheable
@Access(PROPERTY)
@IdClass(RecordId::class)
data class RecordEntity(@Transient private val delegate: Record) : IdentityOf<RecordId>(), Record by delegate {
    @get:Id @get:Column(nullable = false, updatable = false)
    override lateinit var date                : LocalDate
    @get:Id @get:Enumerated(STRING)
    override lateinit var type                : RecordType
    @get:Column(precision = 3, scale = 1)
    override lateinit var hoursSubmitted      : BigDecimal
    override var desc                         : String? = null
    @get:Id
    @get:ManyToOne(optional= false, fetch = LAZY)
    @get:JoinColumn(updatable = false)
    lateinit var report                       : RecordCollectionEntity

    @get:JsonbTransient
    override var id: RecordId
        get() = RecordId(report?.id, date, type)
        set(value) {
            report?.id = value.id
            date = value.date
            type = value.type
        }

    /**
     * @see com.example.marvel.domain.model.jpa.base.IdentityOf
     */
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()

    /**
     * FIXME:
     *    @see https://github.com/quarkusio/quarkus/issues/2196
     *    As a workaround we additionally extend `PanacheRepositoryBase` in our services
     *    @note this is NOT due to it is not managed bean! Any setup won't work from here
     *    e.g. Entities/Repositories wont be enhanced in external jar!
     */
}

inline fun RecordEntity.toRecordDto(): RecordDto = RecordDto(copy(), report.id  ?: 0L)

/**
 * Could be some logic here if we want to distinct between creation and update, for example.
 */
inline fun RecordModel.toRecord(em: EntityManager): RecordEntity = when (this) {
//    is RecordDto           -> RecordEntity(copy()).copyRelations(this, em)
    is RecordCreateCommand -> RecordEntity(copy()).copyRelations(this, em)
    is RecordUpdateCommand -> RecordEntity(copy()).copyRelations(this, em)
}

inline fun RecordEntity.copyRelations(from: RecordModel, em: EntityManager): RecordEntity =
        apply { report = em.getReference(RecordCollectionEntity::class.java, from.recordCollectionId) }
