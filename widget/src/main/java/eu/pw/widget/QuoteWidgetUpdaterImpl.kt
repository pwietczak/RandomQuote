package eu.pw.widget

import android.content.Context
import androidx.glance.appwidget.updateAll
import eu.pw.widget.contract.QuoteWidgetUpdater
import eu.pw.widget.presentation.QuoteWidget
import javax.inject.Inject

internal class QuoteWidgetUpdaterImpl
	@Inject
	constructor(
		private val context: Context,
	) : QuoteWidgetUpdater {
		override suspend fun updateWidgets() {
			QuoteWidget().updateAll(context)
		}
	}