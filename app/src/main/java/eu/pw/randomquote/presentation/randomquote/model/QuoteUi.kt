package eu.pw.randomquote.presentation.randomquote.model

import eu.pw.domain.contract.model.Quote

class QuoteUi(
	val text: String,
	val author: String?,
	val book: String?,
)

fun Quote.toUi() =
	QuoteUi(
		text = text,
		author = author,
		book = book,
	)