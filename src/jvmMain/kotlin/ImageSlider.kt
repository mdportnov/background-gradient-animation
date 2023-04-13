import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import utils.loadImage

@Composable
fun Slider(imageUrls: List<String>, onImageClick: ((ImageBitmap) -> Unit) = {}) {
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
                    modifier = Modifier.size(200.dp).padding(20.dp).shadow(
                        elevation = 50.dp,
                        ambientColor = Color.Blue
                    ),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}