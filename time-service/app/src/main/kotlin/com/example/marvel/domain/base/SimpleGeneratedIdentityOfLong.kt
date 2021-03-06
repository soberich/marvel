package com.example.marvel.domain.base

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.hibernate.id.enhanced.SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY
import org.hibernate.id.enhanced.SequenceStyleGenerator.INCREMENT_PARAM
import org.hibernate.id.enhanced.SequenceStyleGenerator.INITIAL_PARAM
import org.hibernate.id.enhanced.SequenceStyleGenerator.OPT_PARAM
import javax.persistence.Access
import javax.persistence.AccessType.PROPERTY
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.SEQUENCE
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.Transient
import kotlin.properties.Delegates

/**
 * IdentityOf holder for Long identity type.
 */
@MappedSuperclass
@Access(PROPERTY)
abstract class SimpleGeneratedIdentityOfLong : JpaStateTransitionAwareIdentityOf<Long>() {
    //@formatter:off
    @get:
    [Id
    GenericGenerator(
        name = "optimized-sequence",
        strategy = "enhanced-sequence",
        parameters = [
            Parameter(name = CONFIG_PREFER_SEQUENCE_PER_ENTITY , value = "true"),
            Parameter(name = INITIAL_PARAM                     , value = "1"),       //"${SequenceStyleGenerator.DEFAULT_INITIAL_VALUE}"
            Parameter(name = INCREMENT_PARAM                   , value = "1"),      //"${SequenceStyleGenerator.DEFAULT_INCREMENT_PARAM}"
            Parameter(name = OPT_PARAM                         , value = "pooled")]) // org.hibernate.id.enhanced.StandardOptimizerDescriptor
    GeneratedValue(strategy = SEQUENCE, generator = "optimized-sequence")
    Column(nullable = false, updatable = false)]
    @delegate:
    [Transient]
    override var id: Long? by Delegates.vetoable(0L) { _, _, newValue ->
        requireNotNull(newValue) { "${this::class.simpleName} can't be assigned $newValue id" } > 0L
    }
    //@formatter:on
}
