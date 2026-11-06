package eu.pw.widget.presentation.action

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import eu.pw.widget.worker.QuoteWidgetWorkScheduler
import timber.log.Timber

class QuoteWidgetRefreshAction : ActionCallback {
	private companion object {
		private const val TAG = "QuoteWidgetRefreshAction"
	}

	override suspend fun onAction(
		context: Context,
		glanceId: GlanceId,
		parameters: ActionParameters,
	) {
		Timber.tag(TAG).d("onAction called with glanceId: $glanceId and parameters: $parameters")
		QuoteWidgetWorkScheduler.enqueueRefresh(context)
	}
}