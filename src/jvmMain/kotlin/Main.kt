import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val imageUrls = listOf(
        "https://upload.wikimedia.org/wikipedia/en/4/45/Divide_cover.png",
        "https://upload.wikimedia.org/wikipedia/en/a/ad/X_cover.png",
        "https://upload.wikimedia.org/wikipedia/en/c/cd/Ed_Sheeran_-_Equals.png",
    )

    val top3DominantColors = remember { mutableStateOf(listOf(Color.Red, Color.Black, Color.Blue)) }

    val transition = rememberInfiniteTransition()

    val animatedColors = top3DominantColors.value.mapIndexed { index, color ->
        transition.animateColor(
            initialValue = color,
            targetValue = top3DominantColors.value[(index + 1) % top3DominantColors.value.size],
            animationSpec = infiniteRepeatable(
                repeatMode = RepeatMode.Reverse,
                animation = tween(durationMillis = 1000, easing = LinearEasing)
            )
        )
    }

    Window(onCloseRequest = ::exitApplication, title = "Colors") {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().background(
                    brush = Brush.linearGradient(
                        colors = animatedColors.map { it.value }
                    )
                )
            ) {
                Slider(imageUrls = imageUrls) { imageBitmap ->
                    top3DominantColors.value = imageBitmap.getTopDominantColorsDiff().map { it.toComposeColor() }
                }
            }
        }
    }
}