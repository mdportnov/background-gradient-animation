package utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAwtImage
import java.awt.Color
import java.awt.image.BufferedImage

fun ImageBitmap.getTopDominantColors(count: Int = 3, brightnessThreshold: Float = 0.15f): List<Color> {
    val bufferedImage = this.toBufferedImage()
    val colorMap = mutableMapOf<Color, Int>()

    for (y in 0 until bufferedImage.height) {
        for (x in 0 until bufferedImage.width) {
            val awtColor = Color(bufferedImage.getRGB(x, y), true)
            val color = Color(awtColor.red, awtColor.green, awtColor.blue, awtColor.alpha)

            if (color.brightness() >= brightnessThreshold) {
                colorMap[color] = colorMap.getOrDefault(color, 0) + 1
            }
        }
    }

    return colorMap.entries.sortedByDescending { it.value }.take(count).map { it.key }
}

fun ImageBitmap.getTopDominantColorsDiff(count: Int = 3, brightnessThreshold: Float = 0.4f): List<Color> {
    val bufferedImage = this.toBufferedImage()
    val colorMap = mutableMapOf<Color, Int>()

    for (y in 0 until bufferedImage.height) {
        for (x in 0 until bufferedImage.width) {
            val awtColor = Color(bufferedImage.getRGB(x, y), true)
            val color = Color(awtColor.red, awtColor.green, awtColor.blue, awtColor.alpha)

            if (color.brightness() >= brightnessThreshold) {
                val similarColor = colorMap.keys.find { it.isSimilarTo(color) }
                if (similarColor != null) {
                    colorMap[similarColor] = colorMap.getOrDefault(similarColor, 0) + 1
                } else {
                    colorMap[color] = 1
                }
            }
        }
    }

    return colorMap.entries.sortedByDescending { it.value }.take(count).map { it.key }
}

fun ImageBitmap.toBufferedImage(): BufferedImage {
    val awtImage = this.asAwtImage()
    val bufferedImage = BufferedImage(awtImage.getWidth(null), awtImage.getHeight(null), BufferedImage.TYPE_INT_ARGB)

    val graphics2D = bufferedImage.createGraphics()
    graphics2D.drawImage(awtImage, 0, 0, null)
    graphics2D.dispose()

    return bufferedImage
}