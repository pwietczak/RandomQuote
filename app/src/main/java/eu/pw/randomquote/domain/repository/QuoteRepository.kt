package eu.pw.randomquote.domain.repository

import eu.pw.randomquote.domain.model.Quote

interface QuoteRepository {
	suspend fun getRandomQuote(): Result<Quote>
}