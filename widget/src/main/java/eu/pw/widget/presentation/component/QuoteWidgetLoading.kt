package eu.pw.widget.presentation.component

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.wrapContentSize
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import eu.pw.widget.theme.RandomQuoteGlanceTheme

@Composable
internal fun QuoteWidgetLoading() {
	Box(modifier = GlanceModifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		CircularProgressIndicator(
			color = RandomQuoteGlanceTheme.colors.primary,
		)
	}
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview(widthDp = 200, heightDp = 200)
private fun QuoteWidgetLoadingPreview() {
	RandomQuoteGlanceTheme {
		Box(
			modifier =
				GlanceModifier
					.wrapContentSize()
					.background(RandomQuoteGlanceTheme.colors.background)
					.padding(RandomQuoteGlanceTheme.spacing.widgetPadding),
			contentAlignment = Alignment.Center,
		) {
			QuoteWidgetLoading()
		}
	}
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview(widthDp = 200, heightDp = 200)
private fun QuoteWidgetLoadingPreviewDark() {
	RandomQuoteGlanceTheme(dark = true) {
		Box(
			modifier =
				GlanceModifier
					.wrapContentSize()
					.background(RandomQuoteGlanceTheme.colors.background)
					.padding(RandomQuoteGlanceTheme.spacing.widgetPadding),
			contentAlignment = Alignment.Center,
		) {
			QuoteWidgetLoading()
		}
	}
}