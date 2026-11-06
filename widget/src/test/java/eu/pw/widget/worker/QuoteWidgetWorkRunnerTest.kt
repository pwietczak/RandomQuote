package eu.pw.widget.worker

import eu.pw.domain.contract.model.Quote
import eu.pw.domain.contract.usecase.GetRandomQuoteUseCase
import eu.pw.widget.contract.QuoteWidgetUpdater
import eu.pw.widget.data.model.WidgetState
import eu.pw.widget.data.repository.WidgetStateRepository
import eu.pw.widget.usecase.QuoteWidgetUpdateUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class QuoteWidgetWorkRunnerTest {
	private class FakeRepository : WidgetStateRepository {
		override val state: Flow<WidgetState> = MutableStateFlow(WidgetState())

		override suspend fun setLoading() = Unit

		override suspend fun setError() = Unit

		override suspend fun setQuote(quote: Quote) = Unit
	}

	private class FakeWidgetUpdater : QuoteWidgetUpdater {
		override suspend fun updateWidgets() = Unit
	}

	private class FakeGetRandomQuoteUseCase : GetRandomQuoteUseCase {
		var invocationCount = 0
		var result: GetRandomQuoteUseCase.Result =
			GetRandomQuoteUseCase.Result.Success(Quote(text = "A quote.", author = null, book = null))

		override suspend fun invoke(): GetRandomQuoteUseCase.Result {
			invocationCount++
			return result
		}
	}

	private class FakeRuntime : QuoteWidgetRuntime {
		var activeWidgets = true
		var previewPublishCalls = 0

		override suspend fun hasActiveWidgets(): Boolean = activeWidgets

		override suspend fun publishPreviewIfSupported() {
			previewPublishCalls++
		}
	}

	private val getQuote = FakeGetRandomQuoteUseCase()
	private val runtime = FakeRuntime()
	private val runner =
		QuoteWidgetWorkRunner(
			QuoteWidgetUpdateUseCase(getQuote, FakeRepository(), FakeWidgetUpdater()),
			runtime,
		)

	@Test
	fun `run skips update if no widget is active`() =
		runBlocking {
			runtime.activeWidgets = false

			assertEquals(QuoteWidgetWorkRunner.Outcome.SKIPPED, runner.run())
			assertEquals(1, runtime.previewPublishCalls)
			assertEquals(0, getQuote.invocationCount)
		}

	@Test
	fun `run maps successful update`() =
		runBlocking {
			assertEquals(QuoteWidgetWorkRunner.Outcome.SUCCESS, runner.run())
			assertEquals(1, runtime.previewPublishCalls)
			assertEquals(1, getQuote.invocationCount)
		}

	@Test
	fun `run maps failed update`() =
		runBlocking {
			getQuote.result =
				GetRandomQuoteUseCase.Result.Failure(
					eu.pw.domain.contract.error.QuoteError.NETWORK,
				)

			assertEquals(QuoteWidgetWorkRunner.Outcome.FAILURE, runner.run())
			assertEquals(1, runtime.previewPublishCalls)
			assertEquals(1, getQuote.invocationCount)
		}
}