package custom.lib.droid.droid_forge.model.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import custom.lib.droid.droid_forge.model.SecondaryText
import custom.lib.droid.droid_forge.model.invertColor
import custom.lib.droid.droid_forge.model.paddingIntoContainer
import custom.lib.droid.droid_forge.model.systemClip

@Composable
fun ButtonWithIcon(
    value: String,
    ic: Int,
    background: Color,
    contentColor: Color = invertColor(background),
    status: ButtonFillStatus = ButtonFillStatus.FILL,
    onClick: () -> Unit,
) {

    Box(modifier = Modifier.padding(paddingIntoContainer), contentAlignment = Alignment.Center) {

        Box(
            modifier = Modifier
                .clip(systemClip())
                .background(
                    when (status) {
                        ButtonFillStatus.FILL -> background
                        ButtonFillStatus.OUTLINE -> Color.Transparent
                    }
                )
                .border(
                    2.dp,
                    when (status) {
                        ButtonFillStatus.FILL -> Color.Transparent
                        ButtonFillStatus.OUTLINE -> contentColor
                    },
                    systemClip()
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(paddingIntoContainer)
            ) {
                Icon(
                    painter = painterResource(id = ic),
                    contentDescription = "System-status-icon",
                    tint = contentColor,
                    modifier = Modifier.size(25.dp)
                )

                SecondaryText(value = value, color = contentColor)
            }
        }
    }

}

@Composable
fun ButtonWithIcon(
    value: String,
    ic: ImageVector,
    background: Color,
    contentColor: Color = invertColor(background),
    status: ButtonFillStatus = ButtonFillStatus.FILL,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.padding(paddingIntoContainer), contentAlignment = Alignment.Center) {

        Box(
            modifier = Modifier
                .clip(systemClip())
                .background(
                    when (status) {
                        ButtonFillStatus.FILL -> background
                        ButtonFillStatus.OUTLINE -> Color.Transparent
                    }
                )
                .border(
                    2.dp,
                    when (status) {
                        ButtonFillStatus.FILL -> Color.Transparent
                        ButtonFillStatus.OUTLINE -> contentColor
                    },
                    systemClip()
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(paddingIntoContainer)
            ) {
                Icon(
                    imageVector = ic,
                    contentDescription = "System-status-icon",
                    tint = contentColor,
                    modifier = Modifier.size(25.dp)
                )

                SecondaryText(value = value, color = contentColor)
            }
        }
    }


}