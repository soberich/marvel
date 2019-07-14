package com.example.marvel.domain.model.jpa.naming

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource
import org.hibernate.boot.model.naming.ImplicitIndexNameSource
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource
import org.hibernate.boot.model.naming.ImplicitMapKeyColumnNameSource
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl
import org.hibernate.boot.model.naming.ImplicitUniqueKeyNameSource
import java.util.Arrays
import java.util.Comparator.comparing

/**
 * Port from java
 * TODO: Make Inherited entities inherit id path
 * TODO: Make more "kotlinish"
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
        val columnNamesArray =
                if (columnNames.isNullOrEmpty()) emptyArray()
                else columnNames.toTypedArray()

        val sb = StringBuilder()
                .append(tableName).append('_')
        if (referencedTableName != null)
            sb.append("references_").append(referencedTableName).append('_')
        val alphabeticalColumns = columnNamesArray.clone()
        Arrays.sort(alphabeticalColumns, comparing<Identifier, String> { it.canonicalName })
        if (alphabeticalColumns.isNotEmpty())
            sb.append("with")
        for (columnName in alphabeticalColumns)
            sb.append('_').append(PhysicalNamingStrategyImpl.INSTANCE.formatIdentifier(columnName))
        val substring = sb.substring(0, if (sb.length > 59) 59 else sb.length)
        return (prefix ?: "") + substring + (suffix ?: "")
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
