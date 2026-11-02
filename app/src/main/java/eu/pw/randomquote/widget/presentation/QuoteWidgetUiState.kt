package eu.pw.randomquote.widget.presentation

import androidx.compose.runtime.Immutable

@Immutable
sealed interface QuoteWidgetUiState {
	data object Loading : QuoteWidgetUiState

	data class Success(
		val quote: String,
		val author: String?,
		val book: String?,
	) : QuoteWidgetUiState

	data class Error(
		val message: String,
	) : QuoteWidgetUiState
}