import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skia.Image
import java.net.URL

fun main() = application {
    val top3DominantColors = remember { mutableStateOf(listOf(Color.Black, Color.Black, Color.Black)) }

//    val animatedColors = top3DominantColors.value.map { color ->
//        animateColorAsState(targetValue = color).value
//    }
    var version by remember { mutableStateOf(0) }
//    val transition = updateTransition(targetState = top3DominantColors.value, label = "ColorTransition")

    val transition = rememberInfiniteTransition()

    val animatedColors = top3DominantColors.value.mapIndexed { index, color ->
        val progress = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = LinearEasing),
                repeatMode = if (index % 2 == 0) RepeatMode.Reverse else RepeatMode.Reverse
            )
        )

        Color(
            red = color.red * progress.value,
            green = color.green * progress.value,
            blue = color.blue * progress.value,
            alpha = color.alpha * progress.value
        )
    }

    Window(onCloseRequest = ::exitApplication, title = "Colors") {
        val imageUrls = listOf(
            "https://upload.wikimedia.org/wikipedia/en/4/45/Divide_cover.png",
            "https://upload.wikimedia.org/wikipedia/en/a/ad/X_cover.png",
            "https://upload.wikimedia.org/wikipedia/en/c/cd/Ed_Sheeran_-_Equals.png",
            "https://upload.wikimedia.org/wikipedia/ru/thumb/3/31/Tobey_Maguire_as_Spider-Man.jpeg/280px-Tobey_Maguire_as_Spider-Man.jpeg",
            "https://instagram.ftbs5-3.fna.fbcdn.net/v/t51.2885-15/337679753_1260953341175373_5730005991102491280_n.jpg?stp=dst-jpg_e35_p1080x1080&_nc_ht=instagram.ftbs5-3.fna.fbcdn.net&_nc_cat=109&_nc_ohc=DpZhj2mVUncAX8bRCQY&edm=ACWDqb8BAAAA&ccb=7-5&ig_cache_key=MzA2NjM5MjEwOTg3NTc1NTk3MA%3D%3D.2-ccb7-5&oh=00_AfB1G6Wh2sk_ehqByZj9k_lxc-d_gHoz-5nnVcdWOUm7tw&oe=642C4FC8&_nc_sid=1527a3",
            "https://instagram.ftbs5-2.fna.fbcdn.net/v/t51.2885-15/332004674_746058530383474_6467475725195967574_n.jpg?stp=dst-jpg_e35_p720x720&_nc_ht=instagram.ftbs5-2.fna.fbcdn.net&_nc_cat=110&_nc_ohc=DnHtxPMQ1PQAX9O7Sw-&edm=ACWDqb8BAAAA&ccb=7-5&ig_cache_key=MzA1NDc1MTk4MzU2MzQxODk0Ng%3D%3D.2-ccb7-5&oh=00_AfCpxes7wrbnUh0djuUA1rtolIG-2uTl83MWFUTVobXMjw&oe=642D85E8&_nc_sid=1527a3"
        )

        ImageSlider(imageUrls = imageUrls)

        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().background(
                    brush = Brush.linearGradient(
                        colors = animatedColors, start = Offset.Zero, end = Offset.Infinite
//                        colors = top3DominantColors.value, start = Offset.Zero, end = Offset.Infinite
                    )
                )
            ) {
                ImageSlider(imageUrls = imageUrls) { imageBitmap ->
                    top3DominantColors.value = imageBitmap.getTopDominantColorsDiff().map { it.toComposeColor() }
                    version++
//                    top3DominantColors.value.forEach {
//                        println("${it.red}, ${it.green}, ${it.blue}")
//                    }

//                    backgroundColor.value = imageBitmap.getTopDominantColorsDiff(3).random().toComposeColor()
                }
            }
        }
    }
}

@Composable
fun ImageSlider(imageUrls: List<String>, onImageClick: ((ImageBitmap) -> Unit) = {}) {
    LazyRow {
        items(imageUrls) { imageUrl ->
            val imageBitmap = remember(imageUrl) { loadImage(imageUrl) }
            Box(
                modifier = Modifier.fillMaxSize().clickable { onImageClick(imageBitmap) },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Image",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

data class Data(val colors: List<Color>, val version: Int)

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