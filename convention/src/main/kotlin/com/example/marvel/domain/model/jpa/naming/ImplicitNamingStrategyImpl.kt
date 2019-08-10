package com.example.marvel.domain.model.jpa.naming

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource
import org.hibernate.boot.model.naming.ImplicitIndexNameSource
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource
import org.hibernate.boot.model.naming.ImplicitMapKeyColumnNameSource
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl
import org.hibernate.boot.model.naming.ImplicitUniqueKeyNameSource

/**
 * Port from java
 * TODO: Make Inherited entities inherit id path
 */

class ImplicitNamingStrategyImpl : ImplicitNamingStrategyJpaCompliantImpl() {


    override fun determineJoinColumnName(source: ImplicitJoinColumnNameSource): Identifier {
        val name: String =
                if (source.nature == ImplicitJoinColumnNameSource.Nature.ELEMENT_COLLECTION || source.attributePath == null)
                     "${transformEntityName   (source.entityNaming) }_${source.referencedColumnName.text}"
                else "${transformAttributePath(source.attributePath)}_${source.referencedColumnName.text}"

        return toIdentifier(name, source.buildingContext)
    }

    override fun determineForeignKeyName(source: ImplicitForeignKeyNameSource): Identifier =
            source.userProvidedIdentifier ?: toIdentifier(
                    generateName(
                            "FK_",
                            source.tableName,
                            source.referencedTableName,
                            source.columnNames, null
                    ),
                    source.buildingContext
            )

    override fun determineUniqueKeyName(source: ImplicitUniqueKeyNameSource): Identifier =
            source.userProvidedIdentifier ?: toIdentifier(
                    generateName(
                            "UK_",
                            source.tableName, null,
                            source.columnNames, null
                    ),
                    source.buildingContext
            )

    override fun determineIndexName(source: ImplicitIndexNameSource): Identifier =
            source.userProvidedIdentifier ?: toIdentifier(
                    generateName(
                            "IDX_",
                            source.tableName, null,
                            source.columnNames, null
                    ),
                    source.buildingContext
            )

    private fun generateName(prefix: String?,
                             tableName: Identifier,
                             referencedTableName: Identifier?,
                             columnNames: List<Identifier>?,
                             suffix: String?): String {

        val name = "${tableName}_" +
                if (referencedTableName != null) "references_${referencedTableName}_" else ""

        columnNames?.toTypedArray()?.sortedBy { it.canonicalName }?.fold(name + "with") { it, id ->
            it + "_${PhysicalNamingStrategyImpl.INSTANCE.formatIdentifier(id)}"
        }

        return prefix.orEmpty() + name.take(59) + suffix.orEmpty()
    }

    /**
     * FIXME: Does not work
     */
    override fun determineMapKeyColumnName(source: ImplicitMapKeyColumnNameSource): Identifier =
            toIdentifier(
                    "${transformAttributePath(source.pluralAttributePath)}_TYPE",
                    source.buildingContext
            )

    companion object {
        private const val serialVersionUID = -1L
        @JvmStatic
        val INSTANCE: ImplicitNamingStrategyImpl = ImplicitNamingStrategyImpl()
    }
}
