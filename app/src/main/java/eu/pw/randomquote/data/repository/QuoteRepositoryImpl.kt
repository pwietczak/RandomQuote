package eu.pw.randomquote.data.repository

import eu.pw.randomquote.data.mapper.toDomain
import eu.pw.randomquote.data.remote.datasource.QuoteRemoteDataSource
import eu.pw.randomquote.domain.model.Quote
import eu.pw.randomquote.domain.repository.QuoteRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class QuoteRepositoryImpl
	@Inject
	constructor(
		private val remoteDataSource: QuoteRemoteDataSource,
	) : QuoteRepository {
		override suspend fun getRandomQuote(): Result<Quote> =
			runCatching {
				remoteDataSource.getRandomQuote().toDomain()
			}.onFailure { throwable ->
				if (throwable is CancellationException) throw throwable
			}
	}