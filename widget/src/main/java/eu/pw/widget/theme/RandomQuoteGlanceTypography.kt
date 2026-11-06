package eu.pw.widget.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceTheme
import androidx.glance.text.FontFamily
import androidx.glance.text.FontStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.TextAlign
import androidx.glance.text.TextDecoration
import androidx.glance.text.TextStyle

internal val LocalRandomQuoteGlanceTypography =
	compositionLocalOf {
		RandomQuoteGlanceTypography
	}

internal object RandomQuoteGlanceTypography {
	val quote: TextStyle
		@Composable get() =
			TextStyle(
				color = GlanceTheme.colors.onBackground,
				fontSize = 14.sp,
				fontWeight = FontWeight.Normal,
				fontStyle = FontStyle.Normal,
				textAlign = TextAlign.Center,
				textDecoration = TextDecoration.None,
				fontFamily = FontFamily.SansSerif,
			)

	val author: TextStyle
		@Composable get() =
			TextStyle(
				color = GlanceTheme.colors.onBackground,
				fontSize = 12.sp,
				fontWeight = FontWeight.Normal,
				fontStyle = FontStyle.Normal,
				textAlign = TextAlign.Center,
				textDecoration = TextDecoration.None,
				fontFamily = FontFamily.SansSerif,
			)

	val book: TextStyle
		@Composable get() =
			TextStyle(
				color = GlanceTheme.colors.onBackground,
				fontSize = 10.sp,
				fontWeight = FontWeight.Normal,
				fontStyle = FontStyle.Italic,
				textAlign = TextAlign.Center,
				textDecoration = TextDecoration.None,
				fontFamily = FontFamily.SansSerif,
			)
}