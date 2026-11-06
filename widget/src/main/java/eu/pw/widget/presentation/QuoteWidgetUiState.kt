package eu.pw.widget.presentation

import androidx.compose.runtime.Immutable
import eu.pw.widget.data.model.WidgetState

@Immutable
internal sealed interface QuoteWidgetUiState {
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

internal fun WidgetState.toUi(): QuoteWidgetUiState {
	if (isLoading) return QuoteWidgetUiState.Loading
	if (isError) return QuoteWidgetUiState.Error("Failed to load quote.")
	return QuoteWidgetUiState.Success(quote?.text ?: "", quote?.author, quote?.book)
}