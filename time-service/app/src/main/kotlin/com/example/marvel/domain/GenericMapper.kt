package com.example.marvel.domain

import com.example.marvel.domain.base.IdentifiableOf
import org.mapstruct.*
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


/**
 * **see** [Jetbrains Issue Tracker](https://youtrack.jetbrains.com/issue/KT-25960)
 * for why is this not an interface and not split to, say, creator and updater.
 */
@Named
@Singleton
class GenericMapper {

    @set:
    [Inject]
    protected lateinit var em: EntityManager

    @ObjectFactory
    fun <E : IdentifiableOf<Serializable>> @receiver:TargetType Class<E>.objectFactory(@Context id: Serializable?): E? =
        id?.let { em.getReference(this, it) }

    @Mapping(target = "id")
    fun <E : IdentifiableOf<Serializable>> @receiver:TargetType Class<E>.persistentReference(id: Serializable?): E? =
        id?.let { em.find(this, it) ?: em.getReference(this, it) }

    /**
     * FIXME: Exception thrown though `@ObjectFactory` present
     *    The return type com.example.marvel.api.EmployeeCreateView
     *    is an abstract class or interface. Provide a non abstract / non interface result type or a factory method.
     *    public abstract java.util.List<E> toEntityList(@org.jetbrains.annotations.NotNull()
     */
//    abstract fun toEntity(viewIterable: Iterable<CreateCommand>): Collection<E>
//
//    abstract fun toView(commandIterable: Iterable<E>): Collection<CreateCommand>
//
//    abstract fun toEntity(viewStream: Stream<out CreateCommand>): Collection<E>
//
//    abstract fun toView(commandStream: Stream<out E>): Collection<CreateCommand>
//
//    abstract fun toEntityList(viewIterable: Iterable<CreateCommand>): List<E>
//
//    abstract fun toViewList(commandIterable: Iterable<E>): List<CreateCommand>
//
//    abstract fun toEntitySet(viewIterable: Iterable<CreateCommand>): Set<E>
//
//    abstract fun toViewSet(commandIterable: Iterable<E>): Set<CreateCommand>
//
//    abstract fun toEntityList(viewStream: Stream<out CreateCommand>): List<E>
//
//    abstract fun toViewList(commandStream: Stream<out E>): List<CreateCommand>
//
//    abstract fun toEntitySet(viewStream: Stream<out CreateCommand>): Set<E>
//
//    abstract fun toViewSet(commandStream: Stream<out E>): Set<CreateCommand>
}
