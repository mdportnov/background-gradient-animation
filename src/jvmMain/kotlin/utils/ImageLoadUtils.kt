package utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.jetbrains.skia.Image
import java.net.URL

fun loadImage(url: String): ImageBitmap {
    val connection = URL(url).openConnection()
    connection.useCaches = false
    connection.connect()
    connection.getInputStream().use { inputStream ->
        inputStream.use { stream ->
            val image = Image.makeFromEncoded(stream.readBytes())
            return image.asImageBitmap()
        }
    }
}