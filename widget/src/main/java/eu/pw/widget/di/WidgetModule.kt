package eu.pw.widget.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.pw.widget.QuoteWidgetUpdaterImpl
import eu.pw.widget.contract.QuoteWidgetUpdater

@Module
@InstallIn(SingletonComponent::class)
object WidgetModule {
	@Provides
	fun provideQuoteWidgetUpdater(
		@ApplicationContext context: Context,
	): QuoteWidgetUpdater = QuoteWidgetUpdaterImpl(context)
}