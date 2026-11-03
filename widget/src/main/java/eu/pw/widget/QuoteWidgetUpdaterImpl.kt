package eu.pw.widget

import android.content.Context
import androidx.glance.appwidget.updateAll
import dagger.hilt.android.qualifiers.ApplicationContext
import eu.pw.widget.contract.QuoteWidgetUpdater
import eu.pw.widget.presentation.QuoteWidget
import javax.inject.Inject

class QuoteWidgetUpdaterImpl
	@Inject
	constructor(
		@ApplicationContext private val context: Context,
		private val widget: QuoteWidget,
	) : QuoteWidgetUpdater {
		override suspend fun updateWidgets() {
			widget.updateAll(context)
		}
	}