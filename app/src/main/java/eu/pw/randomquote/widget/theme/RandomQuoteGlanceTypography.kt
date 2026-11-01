package eu.pw.randomquote.widget.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.sp
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
	val body =
		TextStyle(
			color = RandomQuoteGlanceColorScheme.colors.onBackground,
			fontSize = 14.sp,
			fontWeight = FontWeight.Normal,
			fontStyle = FontStyle.Normal,
			textAlign = TextAlign.Start,
			textDecoration = TextDecoration.None,
			fontFamily = FontFamily.SansSerif,
		)
}