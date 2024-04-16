package custom.lib.droid.droid_forge.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

@Composable fun systemClip() = MaterialTheme.shapes.medium

val paddingIntoContainer = 15.dp


fun invertColor(color: Color): Color {
    val hex = color.toArgb()
    val invertedHex = 0x00FFFFFF xor hex
    return Color(invertedHex)

}

