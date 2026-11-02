package eu.pw.randomquote.widget.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import eu.pw.randomquote.MainActivity
import eu.pw.randomquote.widget.data.QuoteWidgetState
import eu.pw.randomquote.widget.theme.RandomQuoteGlanceTheme
import timber.log.Timber

class QuoteWidget : GlanceAppWidget() {
	override suspend fun provideGlance(
		context: Context,
		id: GlanceId,
	) {
		provideContent {
			val stateUi = getWidgetState()
			RandomQuoteGlanceTheme {
				val stateUi = stateUi
				QuoteWidgetContent(
					stateUi = stateUi,
				)
			}
		}
	}
}

@Composable
private fun getWidgetState(): QuoteWidgetUiState {
	val preferences = currentState<Preferences>()
	val quoteText = preferences[QuoteWidgetState.quoteTextKey]
	val author = preferences[QuoteWidgetState.authorKey]
	val book = preferences[QuoteWidgetState.bookKey]
	val hasError = preferences[QuoteWidgetState.hasErrorKey] ?: false
	val isLoading = preferences[QuoteWidgetState.isLoading] ?: false

	if (isLoading) {
		return QuoteWidgetUiState.Loading
	}

	if (hasError) {
		return QuoteWidgetUiState.Error("Failed to load quote.")
	}

	if (quoteText == null) {
		Timber.w("Quote text is null in widget state. Returning error state.")
		return QuoteWidgetUiState.Error("Failed to load quote.")
	}

	return QuoteWidgetUiState.Success(
		quote = quoteText,
		author = author,
		book = book,
	)
}

@Composable
private fun QuoteWidgetContent(stateUi: QuoteWidgetUiState) {
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
		when (stateUi) {
			is QuoteWidgetUiState.Loading -> QuoteWidgetLoading()
			is QuoteWidgetUiState.Error -> QuoteWidgetError(stateUi)
			is QuoteWidgetUiState.Success -> QuoteWidgetSuccess(stateUi)
		}
	}
}

@Composable
private fun QuoteWidgetLoading() {
	Text(
		text = "Loading...",
		style = RandomQuoteGlanceTheme.typography.quote,
	)
}

@Composable
private fun QuoteWidgetError(errorState: QuoteWidgetUiState.Error) {
	Column(
		modifier = GlanceModifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalAlignment = Alignment.CenterVertically,
	) {
		Column(
			modifier = GlanceModifier.defaultWeight(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalAlignment = Alignment.CenterVertically,
		) {
			Text(
				text = errorState.message,
				style = RandomQuoteGlanceTheme.typography.quote,
			)
		}
		Button(
			text = "Refresh",
			onClick = actionRunCallback<QuoteWidgetRefreshAction>(),
		)
	}
}

@Composable
private fun QuoteWidgetSuccess(successState: QuoteWidgetUiState.Success) {
	Column(
		modifier = GlanceModifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalAlignment = Alignment.CenterVertically,
	) {
		Column(
			modifier = GlanceModifier.defaultWeight(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalAlignment = Alignment.CenterVertically,
		) {
			Text(
				text = successState.quote,
				style = RandomQuoteGlanceTheme.typography.quote.copy(textAlign = TextAlign.Center),
			)
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
		Button(
			text = "Refresh",
			onClick = actionRunCallback<QuoteWidgetRefreshAction>(),
		)
	}
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview(widthDp = 200, heightDp = 200)
private fun QuoteWidgetLoadingPreview() {
	RandomQuoteGlanceTheme {
		QuoteWidgetContent(
			stateUi = QuoteWidgetUiState.Loading,
		)
	}
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview(widthDp = 200, heightDp = 200)
private fun QuoteWidgetSuccessPreview() {
	RandomQuoteGlanceTheme {
		QuoteWidgetContent(
			stateUi =
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

@OptIn(ExperimentalGlancePreviewApi::class)
@Composable
@Preview(widthDp = 200, heightDp = 200)
private fun QuoteWidgetErrorPreview() {
	RandomQuoteGlanceTheme {
		QuoteWidgetContent(
			stateUi = QuoteWidgetUiState.Error("Failed to load quote."),
		)
	}
}