package eu.pw.widget.data.repository

import eu.pw.domain.contract.model.Quote
import eu.pw.widget.data.model.WidgetState
import kotlinx.coroutines.flow.Flow

internal interface WidgetStateRepository {
	val state: Flow<WidgetState>

	suspend fun setLoading()

	suspend fun setError()

	suspend fun setQuote(quote: Quote)
}