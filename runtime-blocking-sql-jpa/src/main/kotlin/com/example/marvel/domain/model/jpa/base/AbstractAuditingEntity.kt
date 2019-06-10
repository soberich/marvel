package com.example.marvel.domain.model.jpa.base

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 * NO-OP
 * FIXME: `@GeneratedValue`
 * FIXME:
 *    Caused by: java.lang.IllegalStateException: Cannot set final field private final java.lang.String com.example.marvel.domain.model.jpa.base.AbstractAuditingEntity.createdBy from public void com.example.marvel.domain.model.jpa.base.AbstractAuditingEntity.$$_hibernate_write_createdBy(java.lang.String)
 *    at net.bytebuddy.implementation.FieldAccessor$ForImplicitProperty$Appender.apply(FieldAccessor.java:873)
 *    at net.bytebuddy.dynamic.scaffold.TypeWriter$MethodPool$Record$ForDefinedMethod$WithBody.applyCode(TypeWriter.java:713)
 *    at net.bytebuddy.dynamic.scaffold.TypeWriter$MethodPool$Record$ForDefinedMethod$WithBody.applyBody(TypeWriter.java:698)
 *    at net.bytebuddy.dynamic.scaffold.TypeWriter$MethodPool$Record$ForDefinedMethod.apply(TypeWriter.java:605)
 *    at net.bytebuddy.dynamic.scaffold.TypeWriter$Default$ForInlining$WithFullProcessing$RedefinitionClassVisitor.onVisitEnd(TypeWriter.java:4535)
 *    at net.bytebuddy.utility.visitor.MetadataAwareClassVisitor.visitEnd(MetadataAwareClassVisitor.java:271)
 *    at net.bytebuddy.jar.asm.ClassReader.accept(ClassReader.java:683)
 *    at net.bytebuddy.jar.asm.ClassReader.accept(ClassReader.java:391)
 *    at net.bytebuddy.dynamic.scaffold.TypeWriter$Default$ForInlining.create(TypeWriter.java:3393)
 *    at net.bytebuddy.dynamic.scaffold.TypeWriter$Default.make(TypeWriter.java:1930)
 *    at net.bytebuddy.dynamic.scaffold.inline.RedefinitionDynamicTypeBuilder.make(RedefinitionDynamicTypeBuilder.java:217)
 *    at net.bytebuddy.dynamic.DynamicType$Builder$AbstractBase.make(DynamicType.java:3389)
 *    at net.bytebuddy.dynamic.DynamicType$Builder$AbstractBase$Delegator.make(DynamicType.java:3599)
 *    at org.hibernate.bytecode.internal.bytebuddy.ByteBuddyState.make(ByteBuddyState.java:193)
 *    at org.hibernate.bytecode.internal.bytebuddy.ByteBuddyState.rewrite(ByteBuddyState.java:146)
 *    at org.hibernate.bytecode.enhance.internal.bytebuddy.EnhancerImpl.enhance(EnhancerImpl.java:132)
 *    ... 10 more
 */
@MappedSuperclass
abstract class AbstractAuditingEntity<T : Serializable>(
    @Id
    @GeneratedValue
    override
    var id                   : T
) : IdentityOf<T>() {

    @Column(nullable = false, updatable = false)
    @JsonIgnore
    var createdBy           : String? = null

    @Column(nullable = false, updatable = false)
    @JsonIgnore
    var createdDate         : Instant = Instant.now()

    @Column(nullable = false, length = 50)
    @JsonIgnore
    var lastModifiedBy      : String? = null

    @Column(nullable = false)
    @JsonIgnore
    var lastModifiedDate    : Instant = Instant.now()
}
