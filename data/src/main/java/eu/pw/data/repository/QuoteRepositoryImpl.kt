package eu.pw.data.repository

import eu.pw.data.mapper.toDomain
import eu.pw.data.remote.datasource.QuoteRemoteDataSource
import eu.pw.domain.contract.model.Quote
import eu.pw.domain.contract.repository.QuoteRepository
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