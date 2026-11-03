package eu.pw.domain.usecase

import eu.pw.domain.contract.error.QuoteError
import eu.pw.domain.contract.error.QuoteException
import eu.pw.domain.contract.model.Quote
import eu.pw.domain.contract.repository.QuoteRepository
import eu.pw.domain.contract.usecase.GetRandomQuoteUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

class GetRandomQuoteUseCaseImplTest {
	private class FakeQuoteRepository : QuoteRepository {
		var resultToReturn: Result<Quote>? = null
		var exceptionToThrow: Exception? = null

		override suspend fun getRandomQuote(): Result<Quote> {
			exceptionToThrow?.let { throw it }
			return resultToReturn ?: throw IllegalStateException("resultToReturn not set")
		}
	}

	private val repository = FakeQuoteRepository()
	private val useCase = GetRandomQuoteUseCaseImpl(repository)

	@Test
	fun `invoke returns Success when repository succeeds`() =
		runBlocking {
			val expectedQuote =
				Quote(
					text = "The only true wisdom is in knowing you know nothing.",
					author = "Socrates",
					book = "Dialogues",
				)
			repository.resultToReturn = Result.success(expectedQuote)

			val result = useCase()

			assertTrue(result is GetRandomQuoteUseCase.Result.Success)
			assertEquals(expectedQuote, (result as GetRandomQuoteUseCase.Result.Success).quote)
		}

	@Test
	fun `invoke returns Failure with typed error when repository fails with QuoteException`() =
		runBlocking {
			repository.resultToReturn = Result.failure(QuoteException(QuoteError.INVALID_RESPONSE))

			val result = useCase()

			assertTrue(result is GetRandomQuoteUseCase.Result.Failure)
			assertEquals(QuoteError.INVALID_RESPONSE, (result as GetRandomQuoteUseCase.Result.Failure).error)
		}

	@Test
	fun `invoke maps IOException to network error`() =
		runBlocking {
			repository.resultToReturn = Result.failure(IOException("Network error"))

			val result = useCase()

			assertTrue(result is GetRandomQuoteUseCase.Result.Failure)
			assertEquals(QuoteError.NETWORK, (result as GetRandomQuoteUseCase.Result.Failure).error)
		}

	@Test
	fun `invoke maps unknown exception to general error`() =
		runBlocking {
			repository.resultToReturn = Result.failure(IllegalStateException("Unexpected error"))

			val result = useCase()

			assertTrue(result is GetRandomQuoteUseCase.Result.Failure)
			assertEquals(QuoteError.GENERAL, (result as GetRandomQuoteUseCase.Result.Failure).error)
		}

	@Test
	fun `invoke maps thrown exception to general error`() =
		runBlocking {
			repository.exceptionToThrow = IllegalStateException("Unexpected error")

			val result = useCase()

			assertTrue(result is GetRandomQuoteUseCase.Result.Failure)
			assertEquals(QuoteError.GENERAL, (result as GetRandomQuoteUseCase.Result.Failure).error)
		}

	@Test
	fun `invoke propagates cancellation from repository result`() =
		runBlocking {
			val cancellation = CancellationException("Cancelled")
			repository.resultToReturn = Result.failure(cancellation)

			try {
				useCase()
				fail("Expected cancellation to be propagated")
			} catch (actual: CancellationException) {
				assertEquals(cancellation, actual)
			}
		}

	@Test
	fun `invoke propagates cancellation thrown by repository`() =
		runBlocking {
			val cancellation = CancellationException("Cancelled")
			repository.exceptionToThrow = cancellation

			try {
				useCase()
				fail("Expected cancellation to be propagated")
			} catch (actual: CancellationException) {
				assertEquals(cancellation, actual)
			}
		}
}