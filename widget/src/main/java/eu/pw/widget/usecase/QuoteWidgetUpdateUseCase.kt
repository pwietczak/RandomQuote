package eu.pw.widget.usecase

import eu.pw.domain.contract.model.Quote
import eu.pw.domain.contract.usecase.GetRandomQuoteUseCase
import eu.pw.widget.contract.QuoteWidgetUpdater
import eu.pw.widget.data.repository.WidgetStateRepository
import javax.inject.Inject

internal class QuoteWidgetUpdateUseCase
	@Inject
	constructor(
		private val getRandomQuote: GetRandomQuoteUseCase,
		private val dataStore: WidgetStateRepository,
		private val widgetUpdater: QuoteWidgetUpdater,
	) {
		internal sealed interface UpdateResult {
			object Success : UpdateResult

			object Failure : UpdateResult
		}

		internal suspend operator fun invoke(): UpdateResult {
			startLoadingWidget()

			return when (val result = getRandomQuote()) {
				is GetRandomQuoteUseCase.Result.Failure -> onFailure()
				is GetRandomQuoteUseCase.Result.Success -> onSuccess(result.quote)
			}
		}

		private suspend fun onSuccess(quote: Quote): UpdateResult {
			dataStore.setQuote(quote)
			widgetUpdater.updateWidgets()
			return UpdateResult.Success
		}

		private suspend fun onFailure(): UpdateResult {
			dataStore.setError()
			widgetUpdater.updateWidgets()
			return UpdateResult.Failure
		}

		private suspend fun startLoadingWidget() {
			dataStore.setLoading()
			widgetUpdater.updateWidgets()
		}
	}