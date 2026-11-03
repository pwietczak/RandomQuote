package eu.pw.widget.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.pw.widget.QuoteWidgetUpdaterImpl
import eu.pw.widget.contract.QuoteWidgetUpdater

@Module
@InstallIn(SingletonComponent::class)
abstract class WidgetModule {
	@Binds
	abstract fun bindQuoteWidgetUpdater(updaterImpl: QuoteWidgetUpdaterImpl): QuoteWidgetUpdater
}