package eu.pw.randomquote.domain.usecase

import androidx.annotation.StringRes
import eu.pw.randomquote.R
import eu.pw.randomquote.domain.exception.LocalizedException
import eu.pw.randomquote.domain.model.Quote
import eu.pw.randomquote.domain.repository.QuoteRepository
import timber.log.Timber
import javax.inject.Inject

class GetRandomQuoteUseCase
	@Inject
	constructor(
		private val quoteRepository: QuoteRepository,
	) {
		sealed class Result {
			data class Success(
				val quote: Quote,
			) : Result()

			data class Failure(
				@field:StringRes val messageResId: Int,
			) : Result()
		}

		suspend operator fun invoke(): Result =
			quoteRepository
				.getRandomQuote()
				.fold(onSuccess = { quote ->
					Result.Success(quote)
				}, onFailure = { throwable ->
					Timber.e(throwable, "Error loading random quote")
					if (throwable is LocalizedException) {
						Result.Failure(throwable.resId)
					} else {
						Result.Failure(R.string.e_default)
					}
				})
	}