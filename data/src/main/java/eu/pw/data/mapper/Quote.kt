package eu.pw.data.mapper

import eu.pw.data.remote.dto.QuoteDto
import eu.pw.domain.contract.model.Quote

fun QuoteDto.toDomain() =
	Quote(
		text = quote.trim(),
		author =
			author
				?.trim()
				?.takeIf(String::isNotEmpty),
		book =
			book
				?.trim()
				?.takeIf(String::isNotEmpty),
	)