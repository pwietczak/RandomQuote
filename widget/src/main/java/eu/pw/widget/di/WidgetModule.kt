package eu.pw.widget.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.pw.widget.QuoteWidgetUpdaterImpl
import eu.pw.widget.contract.QuoteWidgetUpdater
import eu.pw.widget.worker.GlanceQuoteWidgetRuntime
import eu.pw.widget.worker.QuoteWidgetRuntime

@Module
@InstallIn(SingletonComponent::class)
internal object WidgetModule {
	@Provides
	fun provideQuoteWidgetUpdater(
		@ApplicationContext context: Context,
	): QuoteWidgetUpdater = QuoteWidgetUpdaterImpl(context)

	@Provides
	fun provideQuoteWidgetRuntime(runtime: GlanceQuoteWidgetRuntime): QuoteWidgetRuntime = runtime
}