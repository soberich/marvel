package com.example.marvel.domain.model.jdbc

import com.example.marvel.domain.model.api.record.RecordDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollection
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.jdbc.base.IdentityOf
import io.ebean.Finder
import java.time.Month
import java.time.Year
import javax.persistence.*
import javax.persistence.CascadeType.ALL
import javax.persistence.EnumType.STRING


@Entity
data class RecordCollectionEntity(
    @Id
    @GeneratedValue
    @Column(updatable = false)
    override val id                  : Long,
    @Column(nullable = false)
    override val year                : Year,
    @Column(nullable = false)
    @Enumerated(STRING)
    override val month               : Month,
    @ManyToOne(optional = false)
    @JoinColumn(updatable =false)
    override val project             : ProjectEntity,
    @ManyToOne(optional = false)
    @JoinColumn(updatable =false)
    override val employee            : EmployeeEntity
) : IdentityOf<Long>(), RecordCollection {

    @OneToMany(/*mappedBy = "report", */cascade = [ALL], orphanRemoval = true)
    override lateinit var records: List<RecordEntity>

    /**
     * @see com.example.marvel.domain.model.jdbc.base.IdentityOf
     */
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()

    companion object : Finder<Long, RecordCollectionEntity>(RecordCollectionEntity::class.java)
}


inline fun RecordCollectionDto.toRecordCollection(): RecordCollection = toRecordCollectionEntity()

inline fun RecordCollectionDto.toRecordCollectionEntity()
        = RecordCollectionEntity(id ?: 0, year, month, ProjectEntity.ref(projectId), EmployeeEntity.ref(employeeId))
        .also { it.records = records.map(RecordDto::toRecordEntity) }
