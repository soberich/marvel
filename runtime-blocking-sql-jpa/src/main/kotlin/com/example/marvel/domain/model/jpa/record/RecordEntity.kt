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
import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import org.hibernate.annotations.Immutable
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*
import javax.persistence.EnumType.STRING
import javax.persistence.FetchType.LAZY

/**
 * This could be a just Tuple3
 * but we push to keep hexagonal: less imports (from arrow in this layer) => better.
 */
@Embeddable
data class RecordId(val report: RecordCollectionEntity, val date: LocalDate, val type: RecordType) : Serializable

/**
 * FIXME:
 *  N.B. The `date` is purposefully left first - to check the wrong way.
 */
@Entity
@Immutable
@Cacheable
@IdClass(RecordId::class)
data class RecordEntity(@Transient private val delegate: Record) : IdentityOf<RecordId>(), Record by delegate {
    @Id @Column(updatable = false)
    override lateinit var date                : LocalDate
    @Id @Enumerated(STRING)
    override lateinit var type                : RecordType
    @Column(precision = 3, scale = 1)
    override lateinit var hoursSubmitted      : BigDecimal
    override var desc                         : String? = null
    @Id @ManyToOne(optional= false, fetch = LAZY)
    @JoinColumn(name = "record_collection_id", updatable = false)
    lateinit var report                       : RecordCollectionEntity

    @get:JsonIgnore
    final override
    inline val id: RecordId get() = RecordId(report, date, type)

    /**
     * @see com.example.marvel.domain.model.jpa.base.IdentityOf
     */
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()

    /**
     * FIXME: This does not work!
     *    @see https://github.com/quarkusio/quarkus/issues/2196
     *    As a workaround we additionally extend `PanacheRepositoryBase` in our services
     *    @note this is NOT due to it is not managed bean! Any setup won't work from here
     *    e.g. Entities/Repositories wont be enhanced in external jar!
     */
    companion object : PanacheRepositoryBase<RecordEntity, RecordId>
}

inline fun RecordEntity.toRecordDto(): RecordDto = RecordDto(copy(), report.id  ?: 0L)

/**
 * Could be some logic here if we want to distinct between creation and update, for example.
 */
inline fun RecordModel.toRecord(): RecordEntity = when (this) {
    is RecordDto           -> RecordEntity(copy()).copyRelations(this)
    is RecordCreateCommand -> RecordEntity(copy()).copyRelations(this)
    is RecordUpdateCommand -> RecordEntity(copy()).copyRelations(this)
}

inline fun RecordEntity.copyRelations(from: RecordModel): RecordEntity =
        apply { report = RecordCollectionEntity.findById(from.recordCollectionId) }
