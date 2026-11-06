package eu.pw.widget.data.converter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import eu.pw.domain.contract.model.Quote
import javax.inject.Inject

class QuoteConverterImpl
	@Inject
	constructor() : QuoteConverter {
		private val moshi: Moshi =
			Moshi
				.Builder()
				.addLast(KotlinJsonAdapterFactory())
				.build()

		@OptIn(ExperimentalStdlibApi::class)
		private val jsonAdapter: JsonAdapter<Quote> = moshi.adapter<Quote>()

		override fun toJson(quote: Quote): String = jsonAdapter.toJson(quote)

		override fun toQuote(json: String?): Quote =
			json?.let { jsonAdapter.fromJson(it) } ?: Quote(text = "", author = null, book = null)
	}