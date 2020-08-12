package com.example.marvel.domain.record

import com.example.marvel.api.RecordType
import com.example.marvel.domain.base.AbstractAuditingEntity
import com.example.marvel.domain.recordcollection.RecordCollectionEntity
import org.hibernate.annotations.Immutable
import org.hibernate.annotations.QueryHints.*
import java.io.Serializable
import java.time.Duration
import java.time.LocalDate
import javax.persistence.*
import javax.persistence.AccessType.PROPERTY
import javax.persistence.EnumType.STRING
import javax.persistence.FetchType.LAZY
import kotlin.properties.Delegates

/**
 * FIXME:
 *  N.B. The `date` is purposefully left first - to check the wrong way.
 */
@NamedQuery(
    name = "Record.listForPeriod",
    //language=HQL
    query = """
        SELECT NEW RecordListingView(p.date, p.type, p.hoursSubmitted, p.desc, p.report.id)
        FROM   RecordEntity p
          JOIN p.report c
        WHERE  c.id = :id
           AND c.yearMonth = :yearMonth""",
    hints = [
        QueryHint(name = COMMENT, value = "fetch records for period"),
        QueryHint(name = READ_ONLY, value = "true")])

@Entity
@Immutable
@Cacheable
@Access(PROPERTY)
@IdClass(RecordEntity.RecordId::class)
class RecordEntity : AbstractAuditingEntity<RecordEntity.RecordId>() {
    //@formatter:off
    @get:
    [Id
    Column(nullable = false, updatable = false)]
    var date                                  : LocalDate by Delegates.notNull()
    @get:
    [Id
    Enumerated(STRING)]
    var type                                  : RecordType by Delegates.notNull()
    @get:
    [Column(precision = 5, columnDefinition = "INT")]
    var hoursSubmitted                        : Duration by Delegates.notNull()// will be stored in seconds
    var desc                                  : String? = null
    @get:
    [Id
    ManyToOne(optional= false, fetch = LAZY)
    JoinColumn(updatable = false)]
    var report                                : RecordCollectionEntity by Delegates.notNull()
    //@formatter:on
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
    data class RecordId(var report: RecordCollectionEntity, var date: LocalDate = LocalDate.MIN, var type: RecordType = RecordType.OTHER) : Serializable
}
