package eu.pw.randomquote.widget.worker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import timber.log.Timber
import java.util.concurrent.TimeUnit

object QuoteWidgetWorkScheduler {
	private const val REFRESH_WORK_NAME = "quote_widget_refresh"

	private fun networkConstrains() =
		Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

	fun enqueueRefresh(context: Context) {
		val request =
			OneTimeWorkRequestBuilder<QuoteWidgetWorker>()
				.setConstraints(networkConstrains())
				.setBackoffCriteria(
					backoffPolicy = BackoffPolicy.EXPONENTIAL,
					backoffDelay = WorkRequest.MIN_BACKOFF_MILLIS,
					timeUnit = TimeUnit.MILLISECONDS,
				).build()

		WorkManager.getInstance(context).enqueueUniqueWork(
			uniqueWorkName = REFRESH_WORK_NAME,
			existingWorkPolicy = ExistingWorkPolicy.REPLACE,
			request = request,
		)
		Timber.d("Enqueued QuoteWidgetWorker with ID ${request.id}")
	}
}