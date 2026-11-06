package eu.pw.widget.data

import eu.pw.domain.contract.model.Quote

data class QuoteState(
	val isLoading: Boolean = false,
	val quote: Quote? = null,
	val isError: Boolean = false,
)