import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ma.streamview.android.common.StreamRipple
import com.ma.streamview.android.components.screens.LoadingScreen


@Composable
fun StreamTextButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
    textColor: Color = MaterialTheme.colorScheme.secondary,
    rippleColor: Color = MaterialTheme.colorScheme.primary,
) {
    CompositionLocalProvider(
        LocalRippleTheme provides StreamRipple(color = rippleColor)
    ) {
        TextButton(
            onClick = { onClick() },
            shape = MaterialTheme.shapes.medium,
            modifier = modifier
        ) {
            if (isLoading) {
                LoadingScreen(modifier = Modifier.wrapContentSize(), displayProgressBar = isLoading)
            } else {
                Text(
                    modifier = Modifier
                        .padding(4.dp),
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor
                )
            }
        }
    }
}


@Composable
fun StreamButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
//                .basloqRippleColor(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth(),
        onClick = {
            onClick.invoke()
        },
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 0.dp,
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

