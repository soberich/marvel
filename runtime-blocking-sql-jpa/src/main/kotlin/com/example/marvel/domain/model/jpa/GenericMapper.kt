package com.example.marvel.domain.model.jpa

import com.blazebit.persistence.view.EntityViewManager
import org.mapstruct.Context
import org.mapstruct.ObjectFactory
import org.mapstruct.TargetType
import java.io.Serializable
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * @see [https://youtrack.jetbrains.com/issue/KT-25960] for why is this not an interface and not split to, say, creator and updater.
 */
abstract class GenericMapper<CreateView, CreateCommand, UpdateView, UpdateCommand> {

    @set:
    [PersistenceContext]
    protected lateinit var em: EntityManager

    @set:
    [Inject]
    protected lateinit var evm: EntityViewManager

    @ObjectFactory
    fun @receiver:TargetType Class<CreateView>.objectFactory(): CreateView = evm.create(this)

    abstract fun toEntity(source: CreateCommand): CreateView

    @ObjectFactory
    fun @receiver:TargetType Class<UpdateView>.objectFactory(@Context id: Serializable): UpdateView = evm.getReference(this, id)

    abstract fun toEntity(@Context id: Serializable, source: UpdateCommand): UpdateView

    /**
     * Convenience function to use function reference where possible.
     * @see com.example.marvel.domain.model.jpa.employee.EmployeeBlockingServiceNamespaceImpl.createEmployee
     */
    fun create(createView: CreateView) = evm.update(em, createView)

    /**
     * Convenience function to use function reference where possible.
     * @see com.example.marvel.domain.model.jpa.employee.EmployeeBlockingServiceNamespaceImpl.updateEmployee
     */
    fun update(updateView: UpdateView) = evm.update(em, updateView)

    /**
     * FIXME: Exception thrown though `@ObjectFactory` present
     *    The return type com.example.marvel.domain.model.jpa.employee.EmployeeCreateView
     *    is an abstract class or interface. Provide a non abstract / non interface result type or a factory method.
     *    public abstract java.util.List<CreateView> toEntityList(@org.jetbrains.annotations.NotNull()
     */
//    abstract fun toEntity(viewIterable: Iterable<CreateCommand>): Collection<CreateView>
//
//    abstract fun toView(commandIterable: Iterable<CreateView>): Collection<CreateCommand>
//
//    abstract fun toEntity(viewStream: Stream<out CreateCommand>): Collection<CreateView>
//
//    abstract fun toView(commandStream: Stream<out CreateView>): Collection<CreateCommand>
//
//    abstract fun toEntityList(viewIterable: Iterable<CreateCommand>): List<CreateView>
//
//    abstract fun toViewList(commandIterable: Iterable<CreateView>): List<CreateCommand>
//
//    abstract fun toEntitySet(viewIterable: Iterable<CreateCommand>): Set<CreateView>
//
//    abstract fun toViewSet(commandIterable: Iterable<CreateView>): Set<CreateCommand>
//
//    abstract fun toEntityList(viewStream: Stream<out CreateCommand>): List<CreateView>
//
//    abstract fun toViewList(commandStream: Stream<out CreateView>): List<CreateCommand>
//
//    abstract fun toEntitySet(viewStream: Stream<out CreateCommand>): Set<CreateView>
//
//    abstract fun toViewSet(commandStream: Stream<out CreateView>): Set<CreateCommand>
}
