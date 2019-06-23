package com.example.marvel.domain.model.jpa.naming

import com.example.marvel.utils.Inflector
import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import kotlin.text.RegexOption.IGNORE_CASE

/**
 * Port from java
 * TODO: Make Inherited entities inherit id path
 * TODO: Make more "kotlinish"
 */

class PhysicalNamingStrategyImpl : PhysicalNamingStrategyStandardImpl() {

    override fun toPhysicalTableName(id: Identifier, ctx: JdbcEnvironment): Identifier? {
        val singular = id.text ?: return null
        var plural = singular.replace(ENTITIES, EMPTY)
        plural =
                if (SEQ.matches(plural))
                    Inflector.instance.pluralize(plural)
                else Inflector.instance.pluralize(plural.dropLast(_SEQ.length)) + _SEQ
        return formatIdentifier(id, plural)

    }

    override fun toPhysicalColumnName(id: Identifier, context: JdbcEnvironment): Identifier? = formatIdentifier(id)

    private fun formatIdentifier(id: Identifier, name: String? = id.text): Identifier? {
        name ?: return null
        val formattedName = name.replace(CAMEL, SNAKE).toLowerCase()
        return if (formattedName != name) Identifier.toIdentifier(formattedName, id.isQuoted)
        else id

    }

    companion object {
        val INSTANCE = PhysicalNamingStrategyImpl()
        private const val serialVersionUID = -1L
        private const val SNAKE  = "$1\\_$2"
        private const val DOLLAR = """$"""
        private const val EMPTY  = ""
        private const val _SEQ   = "_seq"

        val ENTITIES = """entity|Entity|Entities|entities""".toRegex()
        val CAMEL    = """((?!^)[^_]+)([A-Z0-9]+)""".toRegex()
//        val CAMEL    = """([a-z]+)([A-Z]+)""".toRegex() //Vlad's
        val SEQ      = """.+(?<!_seq)$""".toRegex(IGNORE_CASE)
    }
}
