package eu.pw.widget.data

data class QuoteState(
	val isLoading: Boolean = false,
	val quote: String? = null,
	val isError: Boolean = false,
)