package eu.pw.widget.theme

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import androidx.glance.material3.ColorProviders
import eu.pw.designsystem.theme.darkScheme
import eu.pw.designsystem.theme.lightScheme

internal val LocalRandomQuoteGlanceColorScheme =
	compositionLocalOf {
		RandomQuoteGlanceColorScheme
	}

internal object RandomQuoteGlanceColorScheme {
	fun colors(context: Context) =
		ColorProviders(
			light = lightScheme(context),
			dark = darkScheme(context),
		)
}