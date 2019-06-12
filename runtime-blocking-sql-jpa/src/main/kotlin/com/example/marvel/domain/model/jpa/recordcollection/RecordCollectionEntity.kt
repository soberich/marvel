@file:Suppress("NOTHING_TO_INLINE", "DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")

package com.example.marvel.domain.model.jpa.recordcollection

import com.example.marvel.domain.model.api.record.RecordModel
import com.example.marvel.domain.model.api.recordcollection.RecordCollection
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionCreateCommand
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionDto
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionModel
import com.example.marvel.domain.model.api.recordcollection.RecordCollectionUpdateCommand
import com.example.marvel.domain.model.jpa.base.SimpleGeneratedIdentityOfLong
import com.example.marvel.domain.model.jpa.employee.EmployeeEntity
import com.example.marvel.domain.model.jpa.project.ProjectEntity
import com.example.marvel.domain.model.jpa.record.RecordEntity
import com.example.marvel.domain.model.jpa.record.toRecord
import com.example.marvel.domain.model.jpa.record.toRecordDto
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import java.time.Month
import java.time.Year
import javax.persistence.Cacheable
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.FetchType.LAZY
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Transient


@Entity
@Cacheable
data class RecordCollectionEntity(@Transient private val delegate: RecordCollection) : SimpleGeneratedIdentityOfLong(), RecordCollection by delegate {
    @Column(nullable = false, updatable = false)
    override lateinit var year                : Year
    @Column(nullable = false, updatable = false)
    @Enumerated(STRING)
    override lateinit var month               : Month
    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(updatable =false)
    lateinit var project                      : ProjectEntity
    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(updatable =false)
    lateinit var employee                     : EmployeeEntity

    @OneToMany(mappedBy = "report", cascade = [ALL], orphanRemoval = true)
    lateinit var records                      : List<RecordEntity>

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
    companion object : PanacheRepositoryBase<RecordCollectionEntity, Long>
}

inline fun RecordCollectionEntity.toRecordCollectionDto(): RecordCollectionDto =
        RecordCollectionDto(copy(), project.id, employee.id  ?: 0L, records.map(RecordEntity::toRecordDto))

inline fun RecordCollectionModel.toRecordCollection(): RecordCollectionEntity = when (this) {
    is RecordCollectionDto           -> RecordCollectionEntity(copy()).copyRelations(this)
    is RecordCollectionCreateCommand -> RecordCollectionEntity(copy()).copyRelations(this)
    is RecordCollectionUpdateCommand -> RecordCollectionEntity(copy()).copyRelations(this)
}

inline fun RecordCollectionEntity.copyRelations(from: RecordCollectionModel): RecordCollectionEntity =
        apply {
            project  = ProjectEntity .findById(from.projectId)
            employee = EmployeeEntity.findById(from.employeeId)
            records  = from.records.map(RecordModel::toRecord)
        }
