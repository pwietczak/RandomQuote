package eu.pw.widget.usecase

import eu.pw.domain.contract.error.QuoteError
import eu.pw.domain.contract.model.Quote
import eu.pw.domain.contract.usecase.GetRandomQuoteUseCase
import eu.pw.widget.contract.QuoteWidgetUpdater
import eu.pw.widget.data.model.WidgetState
import eu.pw.widget.data.repository.WidgetStateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException

class QuoteWidgetUpdateUseCaseTest {
	private class FakeDataStore : WidgetStateRepository {
		override val state: Flow<WidgetState> = MutableStateFlow(WidgetState())
		var setLoadingCallCount = 0
		var setErrorCallCount = 0
		var setQuoteCallCount = 0
		var lastSetQuote: Quote? = null

		var onSetLoading: (suspend () -> Unit)? = null
		var onSetError: (suspend () -> Unit)? = null
		var onSetQuote: (suspend (Quote) -> Unit)? = null

		override suspend fun setLoading() {
			setLoadingCallCount++
			onSetLoading?.invoke()
		}

		override suspend fun setError() {
			setErrorCallCount++
			onSetError?.invoke()
		}

		override suspend fun setQuote(quote: Quote) {
			setQuoteCallCount++
			lastSetQuote = quote
			onSetQuote?.invoke(quote)
		}
	}

	private class FakeQuoteWidgetUpdater : QuoteWidgetUpdater {
		var updateWidgetsCallCount = 0
		var onUpdateWidgets: (suspend () -> Unit)? = null

		override suspend fun updateWidgets() {
			updateWidgetsCallCount++
			onUpdateWidgets?.invoke()
		}
	}

	private class FakeGetRandomQuoteUseCase : GetRandomQuoteUseCase {
		var resultToReturn: GetRandomQuoteUseCase.Result? = null
		var exceptionToThrow: Throwable? = null
		var invokeCount = 0
		var onInvoke: (suspend () -> Unit)? = null

		override suspend fun invoke(): GetRandomQuoteUseCase.Result {
			invokeCount++
			onInvoke?.invoke()
			exceptionToThrow?.let { throw it }
			return resultToReturn ?: throw IllegalStateException("resultToReturn not set")
		}
	}

	private val getRandomQuote = FakeGetRandomQuoteUseCase()
	private val dataStore = FakeDataStore()
	private val widgetUpdater = FakeQuoteWidgetUpdater()

	private val useCase =
		QuoteWidgetUpdateUseCase(
			getRandomQuote = getRandomQuote,
			dataStore = dataStore,
			widgetUpdater = widgetUpdater,
		)

	@Test
	fun `invoke returns Success and updates state and widgets when getRandomQuote succeeds`() =
		runBlocking {
			val expectedQuote =
				Quote(
					text = "Life is what happens when you're busy making other plans.",
					author = "John Lennon",
					book = "Double Fantasy",
				)
			getRandomQuote.resultToReturn = GetRandomQuoteUseCase.Result.Success(expectedQuote)

			val result = useCase()

			assertTrue(result is QuoteWidgetUpdateUseCase.UpdateResult.Success)
			assertEquals(1, dataStore.setLoadingCallCount)
			assertEquals(1, dataStore.setQuoteCallCount)
			assertEquals(expectedQuote, dataStore.lastSetQuote)
			assertEquals(0, dataStore.setErrorCallCount)
			assertEquals(2, widgetUpdater.updateWidgetsCallCount)
		}

	@Test
	fun `invoke returns Failure and updates error state and widgets when getRandomQuote fails`() =
		runBlocking {
			getRandomQuote.resultToReturn = GetRandomQuoteUseCase.Result.Failure(QuoteError.NETWORK)

			val result = useCase()

			assertTrue(result is QuoteWidgetUpdateUseCase.UpdateResult.Failure)
			assertEquals(1, dataStore.setLoadingCallCount)
			assertEquals(1, dataStore.setErrorCallCount)
			assertEquals(0, dataStore.setQuoteCallCount)
			assertEquals(2, widgetUpdater.updateWidgetsCallCount)
		}

	@Test
	fun `invoke executes loading state before fetching quote and updating result state`() =
		runBlocking {
			val expectedQuote = Quote(text = "Knowledge is power.", author = "Francis Bacon", book = null)
			getRandomQuote.resultToReturn = GetRandomQuoteUseCase.Result.Success(expectedQuote)

			val executionOrder = mutableListOf<String>()
			dataStore.onSetLoading = { executionOrder.add("dataStore.setLoading") }
			widgetUpdater.onUpdateWidgets = { executionOrder.add("widgetUpdater.updateWidgets") }
			getRandomQuote.onInvoke = { executionOrder.add("getRandomQuote.invoke") }
			dataStore.onSetQuote = { _ -> executionOrder.add("dataStore.setQuote") }

			useCase()

			val expectedOrder =
				listOf(
					"dataStore.setLoading",
					"widgetUpdater.updateWidgets",
					"getRandomQuote.invoke",
					"dataStore.setQuote",
					"widgetUpdater.updateWidgets",
				)
			assertEquals(expectedOrder, executionOrder)
		}

	@Test
	fun `invoke executes loading state before handling error and updating widgets on failure`() =
		runBlocking {
			getRandomQuote.resultToReturn = GetRandomQuoteUseCase.Result.Failure(QuoteError.GENERAL)

			val executionOrder = mutableListOf<String>()
			dataStore.onSetLoading = { executionOrder.add("dataStore.setLoading") }
			widgetUpdater.onUpdateWidgets = { executionOrder.add("widgetUpdater.updateWidgets") }
			getRandomQuote.onInvoke = { executionOrder.add("getRandomQuote.invoke") }
			dataStore.onSetError = { executionOrder.add("dataStore.setError") }

			useCase()

			val expectedOrder =
				listOf(
					"dataStore.setLoading",
					"widgetUpdater.updateWidgets",
					"getRandomQuote.invoke",
					"dataStore.setError",
					"widgetUpdater.updateWidgets",
				)
			assertEquals(expectedOrder, executionOrder)
		}

	@Test
	fun `invoke propagates exception when getRandomQuote throws exception`() =
		runBlocking {
			val expectedException = RuntimeException("Database error")
			getRandomQuote.exceptionToThrow = expectedException

			try {
				useCase()
				fail("Expected exception to be thrown")
			} catch (actual: RuntimeException) {
				assertEquals(expectedException, actual)
			}

			assertEquals(1, dataStore.setLoadingCallCount)
			assertEquals(0, dataStore.setQuoteCallCount)
			assertEquals(0, dataStore.setErrorCallCount)
			assertEquals(1, widgetUpdater.updateWidgetsCallCount)
		}

	@Test
	fun `invoke propagates CancellationException when getRandomQuote is cancelled`() =
		runBlocking {
			val cancellation = CancellationException("Coroutine cancelled")
			getRandomQuote.exceptionToThrow = cancellation

			try {
				useCase()
				fail("Expected CancellationException to be thrown")
			} catch (actual: CancellationException) {
				assertEquals(cancellation, actual)
			}

			assertEquals(1, dataStore.setLoadingCallCount)
			assertEquals(0, dataStore.setQuoteCallCount)
			assertEquals(0, dataStore.setErrorCallCount)
			assertEquals(1, widgetUpdater.updateWidgetsCallCount)
		}
}