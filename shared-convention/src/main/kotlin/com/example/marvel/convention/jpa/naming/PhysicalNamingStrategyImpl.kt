package com.example.marvel.convention.jpa.naming

import com.example.marvel.convention.utils.Inflector
import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import java.util.Locale.ENGLISH
import kotlin.text.RegexOption.IGNORE_CASE


/**
 * Port from java
 * TODO: Make Inherited entities inherit id path
 * FIXME: Replaces non-portable
 */
//@formatter:off
class PhysicalNamingStrategyImpl : PhysicalNamingStrategyStandardImpl() {

    override fun toPhysicalTableName(id: Identifier?, ctx: JdbcEnvironment?): Identifier? {
        val singular = id?.text ?: return null
        var plural = singular.replace(ENTITIES, EMPTY)
        plural =
                if (SEQ.matches(plural))
                     Inflector.pluralize(plural)
                else Inflector.pluralize(plural.dropLast(_SEQ.length)) + _SEQ
        return formatIdentifier(id, plural)
    }

    override fun toPhysicalSchemaName  (id: Identifier?, ctx: JdbcEnvironment?): Identifier? = formatIdentifier(super.toPhysicalSchemaName(id, ctx))
    override fun toPhysicalCatalogName (id: Identifier?, ctx: JdbcEnvironment?): Identifier? = formatIdentifier(super.toPhysicalCatalogName(id, ctx))
    override fun toPhysicalSequenceName(id: Identifier?, ctx: JdbcEnvironment?): Identifier? = toPhysicalTableName(id, ctx)
    override fun toPhysicalColumnName  (id: Identifier?, ctx: JdbcEnvironment?): Identifier? = formatIdentifier(super.toPhysicalColumnName(id, ctx))

    internal fun formatIdentifier(id: Identifier?, name: String? = id?.text): Identifier? = name?.run {
        replace(CAMEL, SNAKE).toLowerCase(ENGLISH).let {
            return if (it != name) Identifier.toIdentifier(it, id?.isQuoted ?: false)
            else id
        }
    }

    companion object {
        @JvmStatic
        val INSTANCE = PhysicalNamingStrategyImpl()
        private const val SNAKE  = "$1_$2"
        private const val DOLLAR = "$"
        private const val EMPTY  = ""
        private const val _SEQ   = "_seq"

        val ENTITIES = """entity|Entity|Entities|entities""".toRegex()
        val CAMEL    = """(?!^)((?:[A-Z]+(?=[A-Z]))|(?:[a-z](?=[^a-z]))|(?:(?<![0-9])[0-9]+(?=[^0-9])))([^_])""".toRegex()
        val SEQ      = """.+(?<!_seq)$""".toRegex(IGNORE_CASE)
    }
}
//@formatter:on
