package eu.pw.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.pw.domain.contract.repository.QuoteRepository
import eu.pw.domain.contract.usecase.GetRandomQuoteUseCase
import eu.pw.domain.usecase.GetRandomQuoteUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
	@Provides
	fun bindRandomQuoteUseCase(quoteRepository: QuoteRepository): GetRandomQuoteUseCase =
		GetRandomQuoteUseCaseImpl(quoteRepository)
}