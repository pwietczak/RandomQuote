package eu.pw.widget.presentation.component

import androidx.compose.runtime.Composable
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
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
internal fun QuoteWidgetSuccess(successState: QuoteWidgetUiState.Success) {
	Column(
		modifier = GlanceModifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		TopBar(modifier = GlanceModifier.fillMaxWidth(), successState = successState)
		Box(
			modifier = GlanceModifier.defaultWeight(),
			contentAlignment = Alignment.Center,
		) {
			Text(
				text = successState.quote,
				style = RandomQuoteGlanceTheme.typography.quote,
			)
		}
	}
}

@Composable
private fun TopBar(
	modifier: GlanceModifier,
	successState: QuoteWidgetUiState.Success,
) {
	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically,
	) {
		QuoteSource(
			modifier = GlanceModifier.defaultWeight(),
			successState = successState,
		)
		RefreshButton()
	}
}

@Composable
private fun QuoteSource(
	modifier: GlanceModifier,
	successState: QuoteWidgetUiState.Success,
) {
	Column(modifier = modifier) {
		successState.author?.let { author ->
			Text(
				text = author,
				style = RandomQuoteGlanceTheme.typography.author,
			)
		}
		successState.book?.let { book ->
			Text(
				text = book,
				style = RandomQuoteGlanceTheme.typography.book,
			)
		}
	}
}

@Composable
private fun RefreshButton() {
	Image(
		provider = ImageProvider(R.drawable.baseline_refresh_24),
		contentDescription = stringResource(R.string.qw_refresh_button_desc),
		modifier = GlanceModifier.clickable(actionRunCallback<QuoteWidgetRefreshAction>()),
		colorFilter = ColorFilter.tint(RandomQuoteGlanceTheme.colors.onBackground),
	)
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview(widthDp = 200, heightDp = 200)
private fun QuoteWidgetSuccessPreview() {
	RandomQuoteGlanceTheme {
		Box(
			modifier =
				GlanceModifier
					.wrapContentSize()
					.background(RandomQuoteGlanceTheme.colors.background)
					.padding(RandomQuoteGlanceTheme.spacing.widgetPadding),
			contentAlignment = Alignment.Center,
		) {
			QuoteWidgetSuccess(
				QuoteWidgetUiState.Success(
					quote =
						"The more carefully we pay attention to the small details of " +
							"everyday life, the more we appreciate its beauty.",
					author = "A. Example",
					book = "Small Ideas",
				),
			)
		}
	}
}