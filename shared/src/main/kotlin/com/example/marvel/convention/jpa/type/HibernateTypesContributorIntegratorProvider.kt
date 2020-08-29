package com.example.marvel.convention.jpa.type

import com.vladmihalcea.hibernate.type.util.ClassImportIntegrator
import io.github.classgraph.ClassGraph
import io.github.classgraph.ClassInfo
import kotlinx.collections.immutable.toImmutableList
import org.hibernate.boot.model.TypeContributions
import org.hibernate.boot.model.TypeContributor
import org.hibernate.integrator.spi.Integrator
import org.hibernate.service.ServiceRegistry
import org.hibernate.type.BasicType
import org.hibernate.type.CompositeCustomType
import org.hibernate.type.CustomType
import org.hibernate.type.Type
import org.hibernate.usertype.UserType


class HibernateTypesContributorIntegratorProvider<T>(
    classImportIntegrator: Integrator = ClassImportIntegrator(
        ClassGraph()
            //.filterClasspathElements { "marvel" in it } //very unrelyable filtering without knowing exact location
            .whitelistPackages("com.example.marvel")
            .enableAnnotationInfo()
            .removeTemporaryFilesAfterScan()
            .scan().use { result ->
                result
                    .allClasses
                    .filter { !it.isAbstract }
                    //.filter { it.hasAnnotation("io.micronaut.core.annotation.Introspected") }
                    .filter { !it.name.endsWith("Entity") }
                    .map(ClassInfo::loadClass)
                    .toImmutableList()
            })
) : Integrator by classImportIntegrator, TypeContributor where T : UserType, T : Type {

    /**
     * {@inheritDoc}
     */
    @Suppress("UNCHECKED_CAST")
    override fun contribute(typeContributions: TypeContributions, serviceRegistry: ServiceRegistry) {
        val hasPostgres = ClassGraph().filterClasspathElements { "postgresql" in it }.whitelistClasses("org.postgresql.Driver").scan().allClasses.any()
        val hasOracle = ClassGraph().filterClasspathElements { "oracle" in it || "ojdbc" in it }.whitelistClasses("oracle.jdbc.driver.OracleDriver").scan().allClasses.any()
        ClassGraph()
            .filterClasspathElements { "hibernate" in it }
            .whitelistPackages("com.vladmihalcea.hibernate.type")
            .removeTemporaryFilesAfterScan()
            .enableStaticFinalFieldConstantInitializerValues()
            .scan().use { result ->
                val (basicTypes, userTypes) =
                    result.allClasses
                        .filter { !it.isAbstract }
                        .filter { !it.packageName.endsWith(".util") }
                        .filter { it.name.endsWith("Type") }
                        .filter { "YearMonth" !in it.name }
                        .filter { hasPostgres || !it.name.contains("Postgres", ignoreCase = true) }
                        .filter { hasOracle || !it.name.contains("Oracle", ignoreCase = true) }
                        .partition { it.implementsInterface("org.hibernate.type.BasicType") }
                basicTypes
                    .map { it.getFieldInfo("INSTANCE") }
                    .map { it.loadClassAndGetField().get(it.classInfo.loadClass()) }
                    .map { it as BasicType }
                    .forEach(typeContributions::contributeType)
                userTypes
                    .map { it.getFieldInfo("INSTANCE") }
                    .map { it.loadClassAndGetField().get(it.classInfo.loadClass()) }
                    .map { object : /*eliminate deprecations*/CustomType(it as T, arrayOf(it.name)) {} }
                    .forEach(typeContributions::contributeType)
            }
        typeContributions.contributeType(object :/*eliminate deprecations*/ CompositeCustomType(YearMonthType, arrayOf("yearmonth-composite")) {
            override fun getName(): String = "yearmonth-composite"
        })
    }
}
