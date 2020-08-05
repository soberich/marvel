package com.example.marvel.domain.record

import com.example.marvel.api.RecordType
import com.example.marvel.domain.base.AbstractAuditingEntity
import com.example.marvel.domain.recordcollection.RecordCollectionEntity
import io.micronaut.core.annotation.Introspected
import org.hibernate.annotations.Immutable
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*
import javax.persistence.AccessType.PROPERTY
import javax.persistence.EnumType.STRING
import javax.persistence.FetchType.LAZY

/**
 * FIXME:
 *  N.B. The `date` is purposefully left first - to check the wrong way.
 */
@NamedQuery(name = "Record.listForPeriod", query = "SELECT NEW com.example.marvel.domain.record.RecordListingView(p.date, p.type, p.hoursSubmitted, p.desc, p.report.id) FROM RecordEntity p JOIN p.report c WHERE c.id = :id AND c.month = :month AND c.year = :year")

@Entity
@Immutable
@Cacheable
@Access(PROPERTY)
@IdClass(RecordEntity.RecordId::class)
@Introspected
class RecordEntity : AbstractAuditingEntity<RecordEntity.RecordId>() {
    @get:
    [Id
    Column(nullable = false, updatable = false)]
    lateinit var date                         : LocalDate
    @get:
    [Id
    Enumerated(STRING)]
    lateinit var type                         : RecordType
    @get:
    [Column(precision = 3, scale = 1)]
    lateinit var hoursSubmitted               : BigDecimal
    var desc                                  : String? = null
    @get:
    [Id
    ManyToOne(optional= false, fetch = LAZY)
    JoinColumn(updatable = false)]
    lateinit var report                       : RecordCollectionEntity

    @get:
    [Transient]
    override var id: RecordId
        get() = RecordId(report, date, type)
        set(value) {
            report = value.report
            date = value.date
            type = value.type
        }

    /**
     * This could be a just Tuple3
     * but we push to keep hexagonal: less imports (from arrow in this layer) => better.
     */
    @Introspected
    data class RecordId(var report: RecordCollectionEntity, var date: LocalDate = LocalDate.MIN, var type: RecordType = RecordType.OTHER) : Serializable
}
