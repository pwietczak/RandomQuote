package eu.pw.domain.usecase

import eu.pw.domain.contract.error.QuoteError
import eu.pw.domain.contract.error.QuoteException
import eu.pw.domain.contract.repository.QuoteRepository
import eu.pw.domain.contract.usecase.GetRandomQuoteUseCase
import timber.log.Timber
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

internal class GetRandomQuoteUseCaseImpl(
	private val quoteRepository: QuoteRepository,
) : GetRandomQuoteUseCase {
	override suspend operator fun invoke(): GetRandomQuoteUseCase.Result =
		try {
			quoteRepository.getRandomQuote().fold(
				onSuccess = { quote ->
					GetRandomQuoteUseCase.Result.Success(quote)
				},
				onFailure = { throwable ->
					Timber.e(throwable, "Error loading random quote")
					throwable.rethrowIfCancelled()
					GetRandomQuoteUseCase.Result.Failure(throwable.toQuoteError())
				},
			)
		} catch (e: CancellationException) {
			throw e
		} catch (e: Exception) {
			Timber.e(e, "Unexpected error loading random quote")
			GetRandomQuoteUseCase.Result.Failure(e.toQuoteError())
		}

	private fun Throwable.rethrowIfCancelled() {
		if (this is CancellationException) throw this
	}

	private fun Throwable.toQuoteError(): QuoteError =
		when (this) {
			is QuoteException -> error
			is IOException -> QuoteError.NETWORK
			else -> QuoteError.GENERAL
		}
}