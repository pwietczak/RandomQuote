package eu.pw.randomquote.widget.worker

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import eu.pw.randomquote.widget.data.QuoteWidgetUpdateUseCase
import eu.pw.randomquote.widget.presentation.QuoteWidget
import timber.log.Timber

@HiltWorker
class QuoteWidgetWorker
	@AssistedInject
	constructor(
		@Assisted appContext: Context,
		@Assisted params: WorkerParameters,
		private val useCase: QuoteWidgetUpdateUseCase,
	) : CoroutineWorker(appContext, params) {
		override suspend fun doWork(): Result {
			Timber.d("QuoteWidgetWorker started with ID $id")
			val glanceIds =
				GlanceAppWidgetManager(applicationContext).getGlanceIds(QuoteWidget::class.java)
			if (glanceIds.isEmpty()) {
				Timber.d("No QuoteWidget glance IDs found, returning Success")
				return Result.success()
			}

			when (val result = useCase()) {
				QuoteWidgetUpdateUseCase.UpdateResult.Failure -> {
					Timber.d("QuoteWidgetWorker failed to update widget state, returning Failure")
					return Result.failure()
				}
				QuoteWidgetUpdateUseCase.UpdateResult.Success -> {
					Timber.d("QuoteWidgetWorker successfully updated widget state, returning Success")
					return Result.success()
				}
			}
		}
	}