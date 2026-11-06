package eu.pw.widget.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class QuoteWidgetWorker
	@AssistedInject
	internal constructor(
		@Assisted appContext: Context,
		@Assisted params: WorkerParameters,
		private val runner: QuoteWidgetWorkRunner,
	) : CoroutineWorker(appContext, params) {
		private companion object {
			private const val TAG = "QuoteWidgetWorker"
		}

		override suspend fun doWork(): Result {
			Timber.tag(TAG).d("QuoteWidgetWorker started with ID '$id'")
			return when (runner.run()) {
				QuoteWidgetWorkRunner.Outcome.SKIPPED -> {
					Timber.tag(TAG).d("QuoteWidgetWorker skipped work because no active widgets were found")
					Result.success()
				}

				QuoteWidgetWorkRunner.Outcome.FAILURE -> {
					Timber.tag(TAG).d("QuoteWidgetWorker failed to update widget state, returning Failure")
					Result.failure()
				}

				QuoteWidgetWorkRunner.Outcome.SUCCESS -> {
					Timber.tag(TAG).d("QuoteWidgetWorker successfully updated widget state, returning Success")
					Result.success()
				}
			}
		}
	}