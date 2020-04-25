package io.x.ledger.utils

import org.springframework.data.relational.core.mapping.Column
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField

fun <T: Any> Map<String, Any>.toObject(klass: KClass<T>): T =
    mapToObject(this, klass)


private fun <T: Any> mapToObject(source: Map<String, Any>, klass: KClass<T>): T {
    val cons = klass.primaryConstructor!!

    val values = klass.memberProperties.map {
            val columnAnnotation = it.javaField?.getAnnotation(Column::class.java)
            if (columnAnnotation != null) {
                val name = columnAnnotation.value
                it.name to source[name]
            } else {
                it.name to source[toSnakeCase(it.name)]
            }
    }.toMap()
    val arguments = cons.parameters.map { values[it.name] }.toTypedArray()
    return cons.call(*arguments)
}

private fun toSnakeCase(s: String): String {
    val sb = StringBuilder()
    for (i in s.indices) {
        val c = s[i]
        if (c.isUpperCase()) {
            sb.append('_')
            sb.append(c.toLowerCase())
        } else {
            sb.append(c)
        }
    }
    return sb.toString()
}