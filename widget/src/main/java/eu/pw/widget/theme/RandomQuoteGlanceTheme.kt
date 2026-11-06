package eu.pw.widget.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.material3.ColorProviders
import eu.pw.designsystem.theme.darkScheme

internal object RandomQuoteGlanceTheme {
	@Composable
	operator fun invoke(
		dark: Boolean = false,
		content: @Composable () -> Unit,
	) {
		val context = LocalContext.current
		val colors =
			if (dark) {
				ColorProviders(light = darkScheme(context), dark = darkScheme(context))
			} else {
				RandomQuoteGlanceColorScheme.colors(context)
			}
		CompositionLocalProvider(
			LocalRandomQuoteGlanceTypography provides RandomQuoteGlanceTypography,
			LocalRandomQuoteGlanceColorScheme provides RandomQuoteGlanceColorScheme,
		) {
			GlanceTheme(
				colors = colors,
				content = { content.invoke() },
			)
		}
	}

	val spacing: RandomQuoteGlanceSpacing
		@Composable get() = LocalRandomQuoteGlanceSpacing.current

	val typography: RandomQuoteGlanceTypography
		@Composable get() = LocalRandomQuoteGlanceTypography.current

	val colors
		@Composable get() = GlanceTheme.colors
}