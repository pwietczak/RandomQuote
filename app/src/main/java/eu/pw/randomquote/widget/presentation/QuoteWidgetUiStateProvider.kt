package eu.pw.randomquote.widget.presentation

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class QuoteWidgetUiStateProvider : PreviewParameterProvider<QuoteWidgetUiState> {
	override val values: Sequence<QuoteWidgetUiState> =
		sequenceOf(
			QuoteWidgetUiState.Loading,
			shortQuote,
			quoteWithoutAuthor,
			quoteWithoutBook,
			quoteWithoutAttribution,
			longQuote,
			QuoteWidgetUiState.Error("Default error message"),
		)
}

private val shortQuote =
	QuoteWidgetUiState.Success(
		quote = "Stay curious.",
		author = "A. Example",
		book = "Small Ideas",
	)

private val quoteWithoutAuthor =
	QuoteWidgetUiState.Success(
		quote = "A short quote with no author.",
		author = null,
		book = "Notes",
	)

private val quoteWithoutBook =
	QuoteWidgetUiState.Success(
		quote = "A quote with an author, but no book.",
		author = "A. Example",
		book = null,
	)

private val quoteWithoutAttribution =
	QuoteWidgetUiState.Success(
		quote = "A quote with no author or book.",
		author = null,
		book = null,
	)

private val longQuote =
	QuoteWidgetUiState.Success(
		quote =
			"The more carefully we pay attention to the small details of everyday life, " +
				"the more often we discover that curiosity can turn an ordinary moment into " +
				"an opportunity to learn something new.",
		author = "Alexandra Example, Writer and Observer",
		book = "A Rather Long Book Title About Finding Meaning in Everyday Moments",
	)