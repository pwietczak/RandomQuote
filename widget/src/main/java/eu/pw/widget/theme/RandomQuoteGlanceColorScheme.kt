package eu.pw.widget.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.glance.material3.ColorProviders
import eu.pw.designsystem.theme.darkScheme
import eu.pw.designsystem.theme.lightScheme

internal val LocalRandomQuoteGlanceColorScheme =
	compositionLocalOf {
		RandomQuoteGlanceColorScheme
	}

internal object RandomQuoteGlanceColorScheme {
	val colors =
		ColorProviders(
			light = lightScheme,
			dark = darkScheme,
		)
}