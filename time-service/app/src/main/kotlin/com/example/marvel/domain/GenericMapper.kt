package com.example.marvel.domain

import org.mapstruct.Context
import org.mapstruct.ObjectFactory
import org.mapstruct.TargetType
import java.io.Serializable
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * @see [https://youtrack.jetbrains.com/issue/KT-25960] for why is this not an interface and not split to, say, creator and updater.
 */
abstract class GenericMapper<Entity> {

    @set:
    [PersistenceContext]
    protected lateinit var em: EntityManager

    @ObjectFactory
    fun @receiver:TargetType Class<Entity>.objectFactory(@Context id: Serializable): Entity = em.getReference(this, id)

    /**
     * FIXME: Exception thrown though `@ObjectFactory` present
     *    The return type com.example.marvel.api.EmployeeCreateView
     *    is an abstract class or interface. Provide a non abstract / non interface result type or a factory method.
     *    public abstract java.util.List<Entity> toEntityList(@org.jetbrains.annotations.NotNull()
     */
//    abstract fun toEntity(viewIterable: Iterable<CreateCommand>): Collection<Entity>
//
//    abstract fun toView(commandIterable: Iterable<Entity>): Collection<CreateCommand>
//
//    abstract fun toEntity(viewStream: Stream<out CreateCommand>): Collection<Entity>
//
//    abstract fun toView(commandStream: Stream<out Entity>): Collection<CreateCommand>
//
//    abstract fun toEntityList(viewIterable: Iterable<CreateCommand>): List<Entity>
//
//    abstract fun toViewList(commandIterable: Iterable<Entity>): List<CreateCommand>
//
//    abstract fun toEntitySet(viewIterable: Iterable<CreateCommand>): Set<Entity>
//
//    abstract fun toViewSet(commandIterable: Iterable<Entity>): Set<CreateCommand>
//
//    abstract fun toEntityList(viewStream: Stream<out CreateCommand>): List<Entity>
//
//    abstract fun toViewList(commandStream: Stream<out Entity>): List<CreateCommand>
//
//    abstract fun toEntitySet(viewStream: Stream<out CreateCommand>): Set<Entity>
//
//    abstract fun toViewSet(commandStream: Stream<out Entity>): Set<CreateCommand>
}
