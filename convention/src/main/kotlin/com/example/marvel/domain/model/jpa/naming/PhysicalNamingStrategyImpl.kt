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

    override fun toPhysicalTableName(name: Identifier, context: JdbcEnvironment): Identifier {
        var plural = name.text!!.replace(ENTITIES, EMPTY)
        plural =
                if (SEQ.matches(plural))
                    Inflector.instance.pluralize(plural)
                else Inflector.instance.pluralize(plural.dropLast(_SEQ.length)) + _SEQ
        return context.identifierHelper.toIdentifier(
                plural.replace(CAMEL, SNAKE).toLowerCase(),
                name.isQuoted
        )
    }

    override fun toPhysicalColumnName(name: Identifier, context: JdbcEnvironment): Identifier =
            context.identifierHelper.toIdentifier(
                    name.text.replace(DOLLAR, EMPTY).replace(CAMEL, SNAKE).toLowerCase(),
                    name.isQuoted
            )

    companion object {
        private const val serialVersionUID = -1L
        private const val SNAKE  = """$1_$2"""
        private const val DOLLAR = """$"""
        private const val EMPTY  = ""
        private const val _SEQ   = "_seq"

        val ENTITIES = """entity|Entity|Entities|entities""".toRegex()
        val CAMEL    = """((?!^)[^_])([A-Z0-9]+)""".toRegex()
        val SEQ      = """.+(?<!_seq)$""".toRegex(IGNORE_CASE)
    }
}
