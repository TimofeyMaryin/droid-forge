package custom.lib.droid.droid_forge.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun PrimaryText(
    value: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
) {

    ConstructorText(
        value = value,
        color = color,
        fontWeight = fontWeight,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
        textAlign = textAlign,
        maxLines = maxLines,
    )
}

@Composable
fun LargeText(
    value: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
) {
    ConstructorText(
        value = value,
        color = color,
        fontWeight = fontWeight,
        style = MaterialTheme.typography.displaySmall,
        modifier = modifier,
        textAlign = textAlign,
    )
}

@Composable
fun SecondaryText(
    value: String,
    color: Color,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {

    ConstructorText(
        value = value,
        color = color,
        fontWeight = fontWeight,
        style = MaterialTheme.typography.titleMedium,
        textAlign = textAlign,
        modifier = modifier,
        maxLines = maxLines
    )
}

@Composable
fun SmallText(
    value: String,
    color: Color,
    fontWeight: FontWeight = FontWeight.Normal
) {
    ConstructorText(
        value = value,
        color = color,
        fontWeight = fontWeight,
        style = MaterialTheme.typography.titleSmall
    )
}




@Composable
private fun ConstructorText(
    value: String,
    color: Color,
    fontWeight: FontWeight,

    style: TextStyle,
    textAlign: TextAlign = TextAlign.Start,

    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = value,
        color = color,
        fontWeight = fontWeight,

        style = style,
        modifier = modifier,

        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
    )
}