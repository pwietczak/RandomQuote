package eu.pw.randomquote.data.remote.datasource

import eu.pw.randomquote.data.remote.dto.QuoteDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class QuoteRemoteDataSource
	@Inject
	constructor(
		private val httpClient: HttpClient,
	) {
		private companion object {
			const val API_BASE_URL = "https://recite-flax.vercel.app/api/v1"
		}

		suspend fun getRandomQuote(): QuoteDto =
			httpClient
				.get("$API_BASE_URL/random")
				.body()
	}