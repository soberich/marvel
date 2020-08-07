package com.example.marvel.convention.jpa.type

import com.vladmihalcea.hibernate.type.util.ClassImportIntegrator
import io.github.classgraph.ClassGraph
import io.github.classgraph.ClassInfo
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.hibernate.boot.model.TypeContributions
import org.hibernate.boot.model.TypeContributor
import org.hibernate.integrator.spi.Integrator
import org.hibernate.integrator.spi.IntegratorService
import org.hibernate.jpa.boot.spi.IntegratorProvider
import org.hibernate.service.ServiceRegistry
import org.hibernate.type.BasicType
import org.hibernate.type.CustomType
import org.hibernate.type.Type
import org.hibernate.usertype.UserType


class HibernateTypesContributorIntegratorProvider<T> : TypeContributor, IntegratorService, IntegratorProvider where T : UserType, T : Type {

    /**
     * {@inheritDoc}
     */
    @Suppress("UNCHECKED_CAST")
    override fun contribute(typeContributions: TypeContributions, serviceRegistry: ServiceRegistry) {
        val hasPostgres = ClassGraph().whitelistClasses("org.postgresql.Driver").scan().allClasses.any()
        ClassGraph()
            .whitelistPackages("com.vladmihalcea.hibernate.type")
            .filterClasspathElements { it.contains("hibernate") }
            .removeTemporaryFilesAfterScan()
            .enableStaticFinalFieldConstantInitializerValues()
            .scan().use { result ->
                val (basicTypes, userTypes) =
                    result.allClasses
                        .filter { !it.isAbstract }
                        .filter { !it.packageName.endsWith(".util") }
                        .filter { it.name.endsWith("Type") }
                        .filter { !it.name.contains("YearMonth") }
                        .filter { hasPostgres || !it.name.contains("Postgres", ignoreCase = true) }
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
    }

    /**
     * {@inheritDoc}
     */
    //FIXME: This doesn't work. Never called for some reason
    override fun getIntegrators(): List<Integrator> = persistentListOf(ClassImportIntegrator(
        ClassGraph()
            .whitelistPackages("com.vladmihalcea.hibernate.type")
            .removeTemporaryFilesAfterScan()
            .filterClasspathElements { /*FIXME:Debug output*/println("${this::class.simpleName}#getIntegrators -> $it"); true }
            .filterClasspathElements { it.contains("com/example/marvel/domain/") } //FIXME: this is wrong, but the method id not call anyways, which is a bigger problem
            .scan().use { result ->
                result
                    .allClasses
                    .filter { !it.isAbstract }
                    .filter { !it.name.endsWith("Entity") }
                    .map(ClassInfo::loadClass)
                    .toImmutableList()
            }
    ))
}
