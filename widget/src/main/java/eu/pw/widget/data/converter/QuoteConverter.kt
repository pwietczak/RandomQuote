package eu.pw.widget.data.converter

import eu.pw.domain.contract.model.Quote

interface QuoteConverter {
	fun toJson(quote: Quote): String

	fun toQuote(json: String?): Quote
}