package eu.pw.randomquote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.pw.randomquote.data.repository.QuoteRepositoryImpl
import eu.pw.randomquote.domain.repository.QuoteRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun provideQuoteRepository(quoteRepositoryImpl: QuoteRepositoryImpl): QuoteRepository
}