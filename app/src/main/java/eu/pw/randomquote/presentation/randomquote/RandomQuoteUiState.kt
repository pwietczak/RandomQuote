package eu.pw.randomquote.presentation.randomquote

import androidx.annotation.StringRes
import eu.pw.randomquote.presentation.randomquote.model.QuoteUi

sealed interface RandomQuoteUiState {
	data object Loading : RandomQuoteUiState

	data class Success(
		val quote: QuoteUi,
	) : RandomQuoteUiState

	data class Failure(
		@field:StringRes val messageResId: Int,
	) : RandomQuoteUiState
}