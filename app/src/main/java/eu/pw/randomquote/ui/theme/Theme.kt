package eu.pw.randomquote.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import eu.pw.designsystem.theme.darkScheme
import eu.pw.designsystem.theme.lightScheme

@Immutable
data class ColorFamily(
	val color: Color,
	val onColor: Color,
	val colorContainer: Color,
	val onColorContainer: Color,
)

@Composable
fun RandomQuoteTheme(
	// Dynamic color is available on Android 12+. Set to true if you want to enable this
	darkTheme: Boolean = isSystemInDarkTheme(),
	dynamicColor: Boolean = false,
	content: @Composable () -> Unit,
) {
	val colorScheme =
		when {
			dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
				val context = LocalContext.current
				if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
			}

			darkTheme -> darkScheme
			else -> lightScheme
		}

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content,
	)
}