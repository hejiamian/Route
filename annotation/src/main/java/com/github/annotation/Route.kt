package com.github.annotation

import kotlin.reflect.KClass

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
annotation class Route(val value: String) {
}