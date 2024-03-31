package org.example.perlin

data class Vector2(val x: Float, val y: Float) {
    fun dot(dx: Float, dy: Float) = dx * x + dy * y
}