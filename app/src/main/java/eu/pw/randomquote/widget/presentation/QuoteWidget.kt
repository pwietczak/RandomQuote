package eu.pw.randomquote.widget.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.Text
import eu.pw.randomquote.MainActivity
import eu.pw.randomquote.widget.theme.RandomQuoteGlanceTheme

class QuoteWidget : GlanceAppWidget() {
	override suspend fun provideGlance(
		context: Context,
		id: GlanceId,
	) {
		provideContent {
			RandomQuoteGlanceTheme {
				val quoteText = "Quote not found"
				QuoteWidgetContent(
					quoteText = quoteText,
				)
			}
		}
	}
}

@Composable
private fun QuoteWidgetContent(quoteText: String) {
	val context = LocalContext.current
	val intent =
		Intent(context, MainActivity::class.java).apply {
			flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
		}

	Box(
		modifier =
			GlanceModifier
				.fillMaxSize()
				.background(RandomQuoteGlanceTheme.colors.background)
				.clickable(actionStartActivity(intent))
				.padding(16.dp),
		contentAlignment = Alignment.Center,
	) {
		Text(
			text = quoteText,
			style = RandomQuoteGlanceTheme.typography.body,
		)
	}
}

@OptIn(ExperimentalGlancePreviewApi::class) // for preview
@Composable
@Preview(widthDp = 400, heightDp = 200)
private fun QuoteWidgetPreview() {
	RandomQuoteGlanceTheme {
		QuoteWidgetContent(
			quoteText = "Hello widget!",
		)
	}
}