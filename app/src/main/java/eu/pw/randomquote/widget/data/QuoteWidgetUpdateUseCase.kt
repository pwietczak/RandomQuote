package eu.pw.randomquote.widget.data

import androidx.glance.appwidget.updateAll
import dagger.hilt.android.qualifiers.ApplicationContext
import eu.pw.randomquote.domain.model.Quote
import eu.pw.randomquote.domain.usecase.GetRandomQuoteUseCase
import eu.pw.randomquote.widget.presentation.QuoteWidget
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

class QuoteWidgetUpdateUseCase
	@Inject
	constructor(
		@ApplicationContext private val context: android.content.Context,
		private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
		private val dataStore: QuoteDataStore,
		private val quoteWidget: QuoteWidget,
	) {
		sealed interface UpdateResult {
			object Success : UpdateResult

			object Failure : UpdateResult
		}

		suspend operator fun invoke(): UpdateResult =
			try {
				dataStore.setLoading()
				notifyWidget()

				when (val result = getRandomQuoteUseCase()) {
					is GetRandomQuoteUseCase.Result.Failure -> onFailure()
					is GetRandomQuoteUseCase.Result.Success -> onSuccess(result.quote)
				}
			} catch (cancellation: CancellationException) {
				throw cancellation
			} catch (exception: Exception) {
				Timber.e(exception, "Unexpected error while updating quote widget")
				onFailure()
			}

		private suspend fun onSuccess(quote: Quote): UpdateResult {
			dataStore.setQuote(quote.text)
			notifyWidget()
			return UpdateResult.Success
		}

		private suspend fun onFailure(): UpdateResult {
			dataStore.setError()
			notifyWidget()
			return UpdateResult.Failure
		}

		private suspend fun notifyWidget() {
			quoteWidget.updateAll(context = context)
		}
	}