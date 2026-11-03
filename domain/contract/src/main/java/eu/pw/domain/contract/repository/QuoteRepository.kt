package eu.pw.domain.contract.repository

import eu.pw.domain.contract.model.Quote

interface QuoteRepository {
	suspend fun getRandomQuote(): Result<Quote>
}