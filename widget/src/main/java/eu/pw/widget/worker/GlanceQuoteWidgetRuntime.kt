package eu.pw.widget.worker

import android.appwidget.AppWidgetProviderInfo
import android.content.Context
import android.os.Build
import androidx.collection.intSetOf
import androidx.glance.appwidget.GlanceAppWidgetManager
import dagger.hilt.android.qualifiers.ApplicationContext
import eu.pw.widget.QuoteWidgetReceiver
import eu.pw.widget.presentation.QuoteWidget
import timber.log.Timber
import javax.inject.Inject

internal class GlanceQuoteWidgetRuntime
	@Inject
	constructor(
		@param:ApplicationContext private val context: Context,
	) : QuoteWidgetRuntime {
		override suspend fun hasActiveWidgets(): Boolean =
			GlanceAppWidgetManager(context).getGlanceIds(QuoteWidget::class.java).isNotEmpty()

		override suspend fun publishPreviewIfSupported() {
			Timber.d("Attempting to set QuoteWidget Glance preview")
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
				Timber.d("QuoteWidget preview not supported on '${Build.VERSION.SDK_INT}' API level")
				return
			}

			val result =
				GlanceAppWidgetManager(context).setWidgetPreviews(
					QuoteWidgetReceiver::class,
					intSetOf(AppWidgetProviderInfo.WIDGET_CATEGORY_HOME_SCREEN),
				)

			when (result) {
				GlanceAppWidgetManager.SET_WIDGET_PREVIEWS_RESULT_SUCCESS -> {
					Timber.d("QuoteWidget Glance preview published")
				}

				GlanceAppWidgetManager.SET_WIDGET_PREVIEWS_RESULT_RATE_LIMITED -> {
					Timber.w("QuoteWidget Glance preview publication was rate limited")
				}

				else -> error("Unexpected result from publishing QuoteWidget preview: $result")
			}
		}
	}