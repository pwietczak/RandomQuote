package eu.pw.widget.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.glance.GlanceTheme

internal object RandomQuoteGlanceTheme {
	@Composable
	operator fun invoke(content: @Composable () -> Unit) {
		CompositionLocalProvider(
			LocalRandomQuoteGlanceTypography provides RandomQuoteGlanceTypography,
			LocalRandomQuoteGlanceColorScheme provides RandomQuoteGlanceColorScheme,
		) {
			GlanceTheme(
				colors = RandomQuoteGlanceColorScheme.colors,
				content = { content.invoke() },
			)
		}
	}

	val typography: RandomQuoteGlanceTypography
		@Composable get() = LocalRandomQuoteGlanceTypography.current

	val colors
		@Composable get() = GlanceTheme.colors
}