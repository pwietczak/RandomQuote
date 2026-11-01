package eu.pw.randomquote.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import eu.pw.randomquote.widget.presentation.QuoteWidget

class QuoteWidgetReceiver : GlanceAppWidgetReceiver() {
	override val glanceAppWidget: GlanceAppWidget = QuoteWidget()
}