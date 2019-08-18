package com.example.marvel.domain.model.jdbc


import com.example.marvel.domain.model.api.record.Record
import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.enums.RecordType
import com.example.marvel.domain.model.jdbc.base.IdentityOf
import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.Finder
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*
import javax.persistence.EnumType.STRING


/**
 * This could be a just Tuple3
 * but we push to keep hexagonal: less imports (from arrow in this layer) => better.
 */
@Embeddable
data class RecordId(val recordCollectionId: Long, val date: LocalDate, val type: RecordType) : Serializable

@Entity
@IdClass(RecordId::class)
data class RecordEntity(
    @Id
    override val date                : LocalDate,
    @Id
    @Enumerated(STRING)
    override val type                : RecordType,
    @Column(precision = 3, scale = 1)
    override val hoursSubmitted      : BigDecimal,
    override val desc                : String?,
    @Id
    @ManyToOne
    @JoinColumn(name = "record_collection_id", referencedColumnName = "id")
    override val report              : RecordCollectionEntity
) : IdentityOf<RecordId>(), Record {

    @get:
    [JsonIgnore]
    final override inline val id: RecordId get() = RecordId(report.id, date, type)

    /**
     * @see com.example.marvel.domain.model.jdbc.base.IdentityOf
     */
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()



    companion object : Finder<RecordId, RecordEntity>(RecordEntity::class.java)
}

inline fun RecordDto.toRecord(): Record = toRecordEntity()

inline fun RecordDto.toRecordEntity()
        = RecordEntity(date, type, hoursSubmitted, desc, RecordCollectionEntity.ref(recordCollectionId))
