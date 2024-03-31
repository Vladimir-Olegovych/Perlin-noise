package org.example.tests

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR

@Target(CONSTRUCTOR)
@Retention(RUNTIME)
annotation class Inject(val value: String)

fun <T> createInject(type: Class<T>): T {
    val constructor = type.getDeclaredConstructor(String::class.java)
    val annotation = constructor.getDeclaredAnnotation(Inject::class.java)
    return constructor.newInstance(annotation.value)
}