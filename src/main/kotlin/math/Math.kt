package org.example.tests

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotation

@Target(PROPERTY)
@Retention(RUNTIME)
annotation class Math(val value1: Double, val value2: Double, val sing: Char)

class CreateDivide {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Double {
        val annotation = property.findAnnotation<Math>()
        return when(annotation?.sing){
            '+' -> annotation.value1 + annotation.value2
            '-' -> annotation.value1 - annotation.value2
            '*' -> annotation.value1 * annotation.value2
            '/' -> annotation.value1 / annotation.value2
            else -> 0.0
        }
    }
}