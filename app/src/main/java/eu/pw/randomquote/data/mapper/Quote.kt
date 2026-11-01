package eu.pw.randomquote.data.mapper

import eu.pw.randomquote.data.remote.dto.QuoteDto
import eu.pw.randomquote.domain.model.Quote

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