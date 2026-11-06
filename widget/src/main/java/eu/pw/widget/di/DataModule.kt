package eu.pw.widget.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.pw.widget.data.converter.QuoteConverter
import eu.pw.widget.data.converter.QuoteConverterImpl
import eu.pw.widget.data.repository.WidgetStateRepository
import eu.pw.widget.data.repository.WidgetStateRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {
	private const val DATA_STORE_FILE_NAME = "quote_widget_preferences"

	@Provides
	@Singleton
	fun providePreferencesDataStore(
		@ApplicationContext context: Context,
	): DataStore<Preferences> =
		PreferenceDataStoreFactory.create(
			produceFile = { context.preferencesDataStoreFile(DATA_STORE_FILE_NAME) },
		)
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class QuoteConverterModule {
	@Binds
	@Singleton
	internal abstract fun provideQuoteConverter(impl: QuoteConverterImpl): QuoteConverter
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class QuoteWidgetStateRepositoryModule {
	@Binds
	@Singleton
	internal abstract fun provideQuoteWidgetStateRepository(
		impl: WidgetStateRepositoryImpl,
	): WidgetStateRepository
}