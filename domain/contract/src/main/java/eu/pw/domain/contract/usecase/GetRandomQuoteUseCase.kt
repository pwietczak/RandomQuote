package eu.pw.domain.contract.usecase

import eu.pw.domain.contract.error.QuoteError
import eu.pw.domain.contract.model.Quote

interface GetRandomQuoteUseCase {
	sealed class Result {
		data class Success(
			val quote: Quote,
		) : Result()

		data class Failure(
			val error: QuoteError,
		) : Result()
	}

	suspend operator fun invoke(): Result
}