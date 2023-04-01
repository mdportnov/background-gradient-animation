import java.awt.Color
import kotlin.math.sqrt

fun Color.toComposeColor(): androidx.compose.ui.graphics.Color {
    return androidx.compose.ui.graphics.Color(red, green, blue, alpha)
}

fun Color.brightness(): Float {
    return (red + green + blue) / (3 * 255f)
}

fun Color.isSimilarTo(other: Color, threshold: Float = 80f): Boolean {
    val distance = sqrt(
        ((red - other.red) * (red - other.red)
                + (green - other.green) * (green - other.green)
                + (blue - other.blue) * (blue - other.blue)).toDouble()
    )
    return distance < threshold
}