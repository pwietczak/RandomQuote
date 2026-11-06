package eu.pw.widget.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

internal val LocalRandomQuoteGlanceSpacing =
	compositionLocalOf {
		RandomQuoteGlanceSpacing
	}

internal object RandomQuoteGlanceSpacing {
	val widgetPadding = 12.dp
}