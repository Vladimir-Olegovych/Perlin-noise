package org.example

import org.example.perlin.Noise
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.abs


fun main() {
    val size = 1000
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
    val noise = Noise(71234012)

    var per = 0
    for (y in 0 until size) {
        val percent = (y * 100) / size
        for (x in 0 until size) {
            val n = abs(noise.noise(x, y))
            val color = Color(n, n, n)
            image.setRGB(x, y, color.rgb)
        }
        if (percent != per) {
            println(percent)
            per = percent
        }
    }
    println(100)

    try {
        val output = File("noise_image.png")
        ImageIO.write(image, "png", output)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}