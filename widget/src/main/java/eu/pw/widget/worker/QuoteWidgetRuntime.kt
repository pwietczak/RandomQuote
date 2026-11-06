package eu.pw.widget.worker

internal interface QuoteWidgetRuntime {
	suspend fun hasActiveWidgets(): Boolean

	suspend fun publishPreviewIfSupported()
}