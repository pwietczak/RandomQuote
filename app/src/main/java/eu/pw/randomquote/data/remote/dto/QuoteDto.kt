package eu.pw.randomquote.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteDto(
	@SerialName("quote") val quote: String,
	val author: String? = null,
	val book: String? = null,
)