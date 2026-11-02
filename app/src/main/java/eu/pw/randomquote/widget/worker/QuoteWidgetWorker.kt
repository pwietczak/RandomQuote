package eu.pw.randomquote.widget.worker

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import eu.pw.randomquote.domain.model.Quote
import eu.pw.randomquote.domain.repository.QuoteRepository
import eu.pw.randomquote.widget.data.QuoteWidgetState
import eu.pw.randomquote.widget.presentation.QuoteWidget
import timber.log.Timber

@HiltWorker
class QuoteWidgetWorker
	@AssistedInject
	constructor(
		@Assisted appContext: Context,
		@Assisted params: WorkerParameters,
		private val quoteRepository: QuoteRepository,
	) : CoroutineWorker(appContext, params) {
		override suspend fun doWork(): Result {
			val glanceIds =
				GlanceAppWidgetManager(applicationContext).getGlanceIds(QuoteWidget::class.java)
			if (glanceIds.isEmpty()) {
				Timber.d("No QuoteWidget glance IDs found, returning Success")
				return Result.success()
			}

			return try {
				quoteRepository.getRandomQuote().fold(
					onSuccess = { quote ->
						Timber.d("Successfully fetched quote: $quote")
						for (glanceId in glanceIds) {
							setWidgetState(quote, glanceId)
							QuoteWidget().update(applicationContext, glanceId)
						}
						Result.success()
					},
					onFailure = { error ->
						Timber.e(error, "Failed to fetch quote")
						for (glanceId in glanceIds) {
							setWidgetState(null, glanceId)
							QuoteWidget().update(applicationContext, glanceId)
						}
						Result.failure()
					},
				)
			} catch (e: Exception) {
				Timber.e(e, "Exception occurred while fetching quote")
				Result.failure()
			}
		}

		private suspend fun setWidgetState(
			quote: Quote?,
			glanceId: GlanceId,
		) {
			updateAppWidgetState(
				context = applicationContext,
				glanceId = glanceId,
			) {
				it[QuoteWidgetState.isLoading] = false

				if (quote == null) {
					it[QuoteWidgetState.hasErrorKey] = true
					return@updateAppWidgetState
				}

				it[QuoteWidgetState.quoteTextKey] = quote.text
				quote.author?.let { author -> it[QuoteWidgetState.authorKey] = author }
				quote.book?.let { book -> it[QuoteWidgetState.bookKey] = book }
				it[QuoteWidgetState.hasErrorKey] = false
			}
		}
	}