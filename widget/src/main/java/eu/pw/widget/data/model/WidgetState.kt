package eu.pw.widget.data.model

import eu.pw.domain.contract.model.Quote

internal data class WidgetState(
	val isLoading: Boolean = false,
	val quote: Quote? = null,
	val isError: Boolean = false,
)