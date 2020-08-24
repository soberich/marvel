package com.example.marvel.convention.jpa.type

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.type.StandardBasicTypes.*
import org.hibernate.type.Type
import org.hibernate.usertype.CompositeUserType
import java.io.Serializable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.YearMonth

/**
 * Non-nullable [CompositeUserType] of [YearMonth]
 * Neither of components can be null as [YearMonth] does not make sense otherwise
 */
object YearMonthType : CompositeUserType {
    override fun getPropertyNames(): Array<String> = arrayOf("year", "month")

    override fun getPropertyTypes(): Array<Type> = arrayOf(SHORT, BYTE)

    override fun getPropertyValue(component: Any, property: Int): Any =
        if (property == 0) (component as YearMonth).year else (component as YearMonth).monthValue

    override fun setPropertyValue(component: Any, property: Int, value: Any): Unit =
        throw UnsupportedOperationException()

    override fun returnedClass(): Class<*> = YearMonth::class.java

    override fun equals(x: Any, y: Any): Boolean =
        x === y || (x as YearMonth).year == (y as YearMonth).year && x.monthValue == y.monthValue

    override fun hashCode(x: Any): Int = x.hashCode()

    override fun nullSafeGet(rs: ResultSet, names: Array<String>, session: SharedSessionContractImplementor, owner: Any): Any =
        YearMonth.of(SHORT.nullSafeGet(rs, names[0], session).toInt(), BYTE.nullSafeGet(rs, names[1], session).toInt())

    override fun nullSafeSet(st: PreparedStatement, value: Any, index: Int, session: SharedSessionContractImplementor) {
        SHORT.nullSafeSet(st, (value as YearMonth).year.toShort(), index, session)
        BYTE.nullSafeSet(st, value.monthValue.toByte(), index + 1, session)
    }

    override fun deepCopy(value: Any): Any = YearMonth.of((value as YearMonth).year, value.monthValue)

    override fun isMutable(): Boolean = false

    override fun disassemble(value: Any, session: SharedSessionContractImplementor): Serializable =
        deepCopy(value) as Serializable

    override fun assemble(cached: Serializable, session: SharedSessionContractImplementor, owner: Any): Any =
        deepCopy(cached)

    override fun replace(original: Any, target: Any, session: SharedSessionContractImplementor, owner: Any): Any =
        deepCopy(original)
}
