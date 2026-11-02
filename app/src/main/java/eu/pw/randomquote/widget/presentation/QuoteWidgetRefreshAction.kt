package eu.pw.randomquote.widget.presentation

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import eu.pw.randomquote.widget.data.QuoteWidgetState
import eu.pw.randomquote.widget.worker.QuoteWidgetWorkScheduler
import timber.log.Timber

class QuoteWidgetRefreshAction : ActionCallback {
	override suspend fun onAction(
		context: Context,
		glanceId: GlanceId,
		parameters: ActionParameters,
	) {
		Timber.d("QuoteWidgetRefreshAction triggered for widgetId: $glanceId.")
		updateAppWidgetState(context, glanceId) { prefs ->
			prefs[QuoteWidgetState.isLoading] = true
		}
		QuoteWidget().update(context, glanceId)

		QuoteWidgetWorkScheduler.enqueueRefresh(context)
	}
}