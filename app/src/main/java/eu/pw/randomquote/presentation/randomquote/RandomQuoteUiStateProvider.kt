package eu.pw.randomquote.presentation.randomquote

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import eu.pw.randomquote.presentation.randomquote.model.QuoteUi

class RandomQuoteUiStateProvider : PreviewParameterProvider<RandomQuoteUiState> {
	override val values: Sequence<RandomQuoteUiState> =
		sequenceOf(
			// RandomQuoteUiState.Loading,
			shortQuote, // quoteWithoutAuthor,
			// quoteWithoutBook,
			// quoteWithoutAttribution,
			// longQuote,
			// RandomQuoteUiState.Failure(R.string.e_default),
		)
}

private val shortQuote =
	RandomQuoteUiState.Success(
		QuoteUi(
			text = "Stay curious.",
			author = "A. Example",
			book = "Small Ideas",
		),
	)

private val quoteWithoutAuthor =
	RandomQuoteUiState.Success(
		QuoteUi(
			text = "A short quote with no author.",
			author = null,
			book = "Notes",
		),
	)

private val quoteWithoutBook =
	RandomQuoteUiState.Success(
		QuoteUi(
			text = "A quote with an author, but no book.",
			author = "A. Example",
			book = null,
		),
	)

private val quoteWithoutAttribution =
	RandomQuoteUiState.Success(
		QuoteUi(
			text = "A quote with no author or book.",
			author = null,
			book = null,
		),
	)

private val longQuote =
	RandomQuoteUiState.Success(
		QuoteUi(
			text =
				"The more carefully we pay attention to the small details of everyday life, " +
					"the more often we discover that curiosity can turn an ordinary moment into " +
					"an opportunity to learn something new.",
			author = "Alexandra Example, Writer and Observer",
			book = "A Rather Long Book Title About Finding Meaning in Everyday Moments",
		),
	)