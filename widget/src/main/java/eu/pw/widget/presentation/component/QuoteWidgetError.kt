package eu.pw.widget.presentation.component

import androidx.compose.runtime.Composable
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.components.OutlineButton
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.wrapContentSize
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.Text
import eu.pw.widget.R
import eu.pw.widget.presentation.QuoteWidgetUiState
import eu.pw.widget.presentation.action.QuoteWidgetRefreshAction
import eu.pw.widget.presentation.util.stringResource
import eu.pw.widget.theme.RandomQuoteGlanceTheme

@Composable
internal fun QuoteWidgetError(errorState: QuoteWidgetUiState.Error) {
	Column(
		modifier = GlanceModifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalAlignment = Alignment.CenterVertically,
	) {
		Box(
			modifier = GlanceModifier.defaultWeight(),
			contentAlignment = Alignment.Center,
		) {
			Text(
				text = errorState.message,
				style = RandomQuoteGlanceTheme.typography.quote,
			)
		}
		RetryButton()
	}
}

@Composable
private fun RetryButton() {
	OutlineButton(
		text = stringResource(R.string.qw_button_retry),
		onClick = actionRunCallback<QuoteWidgetRefreshAction>(),
		contentColor = RandomQuoteGlanceTheme.colors.primary,
		modifier = GlanceModifier,
		maxLines = 1,
	)
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview(widthDp = 200, heightDp = 200)
private fun QuoteWidgetErrorPreview() {
	RandomQuoteGlanceTheme {
		Box(
			modifier =
				GlanceModifier
					.wrapContentSize()
					.background(RandomQuoteGlanceTheme.colors.background)
					.padding(RandomQuoteGlanceTheme.spacing.widgetPadding),
			contentAlignment = Alignment.Center,
		) {
			QuoteWidgetError(QuoteWidgetUiState.Error("Failed to load quote."))
		}
	}
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview(widthDp = 200, heightDp = 200)
private fun QuoteWidgetErrorPreviewDark() {
	RandomQuoteGlanceTheme(dark = true) {
		Box(
			modifier =
				GlanceModifier
					.wrapContentSize()
					.background(RandomQuoteGlanceTheme.colors.background)
					.padding(RandomQuoteGlanceTheme.spacing.widgetPadding),
			contentAlignment = Alignment.Center,
		) {
			QuoteWidgetError(QuoteWidgetUiState.Error("Failed to load quote."))
		}
	}
}