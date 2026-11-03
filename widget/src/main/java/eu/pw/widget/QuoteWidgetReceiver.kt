package eu.pw.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import dagger.hilt.android.AndroidEntryPoint
import eu.pw.widget.presentation.QuoteWidget
import javax.inject.Inject

@AndroidEntryPoint
class QuoteWidgetReceiver : GlanceAppWidgetReceiver() {
	@Inject
	lateinit var quoteWidget: QuoteWidget

	override val glanceAppWidget: GlanceAppWidget
		get() = quoteWidget
}