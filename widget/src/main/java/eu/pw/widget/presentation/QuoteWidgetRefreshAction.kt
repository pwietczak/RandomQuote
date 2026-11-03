package eu.pw.widget.presentation

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import eu.pw.widget.worker.QuoteWidgetWorkScheduler

class QuoteWidgetRefreshAction : ActionCallback {
	override suspend fun onAction(
		context: Context,
		glanceId: GlanceId,
		parameters: ActionParameters,
	) {
		QuoteWidgetWorkScheduler.enqueueRefresh(context)
	}
}