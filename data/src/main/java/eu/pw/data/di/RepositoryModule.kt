package eu.pw.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.pw.data.repository.QuoteRepositoryImpl
import eu.pw.domain.contract.repository.QuoteRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun provideQuoteRepository(quoteRepositoryImpl: QuoteRepositoryImpl): QuoteRepository
}