package eu.pw.widget.presentation

import eu.pw.domain.contract.model.Quote
import eu.pw.widget.data.model.WidgetState
import org.junit.Assert.assertEquals
import org.junit.Test

class QuoteWidgetUiStateTest {
	@Test
	fun `toUi prioritizes loading over error`() {
		assertEquals(
			QuoteWidgetUiState.Loading,
			WidgetState(isLoading = true, isError = true).toUi(),
		)
	}

	@Test
	fun `toUi maps error state to error message`() {
		assertEquals(
			QuoteWidgetUiState.Error("Failed to load quote."),
			WidgetState(isError = true).toUi(),
		)
	}

	@Test
	fun `toUi maps quote and optional attribution`() {
		val quote = Quote(text = "Stay curious.", author = "A. Example", book = null)

		assertEquals(
			QuoteWidgetUiState.Success(quote = "Stay curious.", author = "A. Example", book = null),
			WidgetState(quote = quote).toUi(),
		)
	}

	@Test
	fun `toUi maps missing quote to empty success state`() {
		assertEquals(
			QuoteWidgetUiState.Success(quote = "", author = null, book = null),
			WidgetState().toUi(),
		)
	}
}