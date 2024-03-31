package org.example.perlin

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random

class Noise(private val seed: Int) {

    private val angleOffset = 0F

    private val min = -1F
    private val max = 1F

    private val contrast = 1.2F
    private val octaves = 12
    private val frequencies = (0 until octaves).map { 2f.pow(it) }.toList()

    private val gridSize = 300
    private val scale = (max - min) / 2f

    fun noise(x: Int, y: Int): Float {
        val value = frequencies.map { frequency ->
            perlin(x * frequency / gridSize, y * frequency / gridSize) / frequency
        }.sum()
        return min + ((value * contrast).coerceIn(-1F, 1F) + 1F) * scale
    }

    private fun perlin(x: Float, y: Float): Float {
        val x0 = x.toInt()
        val y0 = y.toInt()

        val x1 = x0 + 1
        val y1 = y0 + 1

        val sx = x - x0.toFloat()
        val sy = y - y0.toFloat ()

        val topL = cornerValue(x0, y0, x, y)
        val topR = cornerValue(x1, y0, x, y)
        val bottomL = cornerValue(x0, y1, x, y)
        val bottomR = cornerValue(x1, y1, x, y)

        val top = cubicInterpolate(topL, topR, sx)
        val bottom = cubicInterpolate(bottomL, bottomR, sx)
        return cubicInterpolate(top, bottom, sy)
    }

    private fun cubicInterpolate(min: Float, max: Float, value: Float): Float {
        val v = value.coerceIn(0f, 1f)
        return  ((max - min) * (3f - v * 2f) * v * v + min)
    }

    private fun cornerValue(ix: Int, iy: Int, x: Float, y: Float): Float {
        val dx = x - ix.toFloat()
        val dy = y - iy.toFloat()
        return randomGradient(ix, iy).dot(dx, dy)
    }

    private fun <in1, in2, Out> memoized(fn: (in1, in2) -> Out): (in1, in2) -> Out {
        val cache: MutableMap<Pair<in1, in2>, Out> = HashMap()
        return  { a, b ->
            cache.getOrPut(Pair(a, b)) { fn(a, b) }
        }
    }

    private val randomGradient = memoized<Int, Int, Vector2> { x, y ->
        val angle = Random(seed * (79701 * x + 49936 * y)).nextDouble(0.0, 2 * PI).toFloat() + angleOffset
        Vector2(sin(angle), cos(angle))
    }
}