package eu.pw.widget.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import eu.pw.domain.contract.model.Quote
import eu.pw.widget.data.converter.QuoteConverter
import eu.pw.widget.data.converter.QuoteConverterImpl
import eu.pw.widget.data.repository.WidgetStateRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class WidgetStateRepositoryImplTest {
	private class FakeDataStore(
		initialPreferences: Preferences = emptyPreferences(),
	) : DataStore<Preferences> {
		private val _data = MutableStateFlow(initialPreferences)
		override val data: Flow<Preferences> = _data

		override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences {
			val updated = transform(_data.value)
			_data.value = updated
			return updated
		}
	}

	private class FakeQuoteConverter : QuoteConverter {
		var toJsonCallCount = 0
		var toQuoteCallCount = 0
		var lastQuoteToJson: Quote? = null

		override fun toJson(quote: Quote): String {
			toJsonCallCount++
			lastQuoteToJson = quote
			return """{"text":"${quote.text}"}"""
		}

		override fun toQuote(json: String?): Quote {
			toQuoteCallCount++
			return if (json != null) {
				Quote(text = "parsed", author = null, book = null)
			} else {
				Quote(text = "", author = null, book = null)
			}
		}
	}

	private val fakeDataStore = FakeDataStore()
	private val quoteConverter = QuoteConverterImpl()
	private val widgetStateRepositoryImpl = WidgetStateRepositoryImpl(fakeDataStore, quoteConverter)

	@Test
	fun `initial state returns default QuoteState when DataStore is empty`() =
		runBlocking {
			val currentState = widgetStateRepositoryImpl.state.first { it.quote != null }

			assertFalse(currentState.isLoading)
			assertFalse(currentState.isError)
			assertEquals("", currentState.quote?.text)
			assertNull(currentState.quote?.author)
			assertNull(currentState.quote?.book)
		}

	@Test
	fun `initial state maps pre-populated DataStore error preference correctly`() =
		runBlocking {
			fakeDataStore.updateData { prefs ->
				prefs.toMutablePreferences().apply {
					this[booleanPreferencesKey("quote_widget_error")] = true
				}
			}

			val currentState = widgetStateRepositoryImpl.state.first { it.isError }
			assertTrue(currentState.isError)
			assertFalse(currentState.isLoading)
		}

	@Test
	fun `setLoading sets isLoading to true and isError to false`() =
		runBlocking {
			widgetStateRepositoryImpl.setLoading()

			val currentState = widgetStateRepositoryImpl.state.first { it.isLoading }
			assertTrue(currentState.isLoading)
			assertFalse(currentState.isError)
		}

	@Test
	fun `setError sets isError to true and isLoading to false`() =
		runBlocking {
			widgetStateRepositoryImpl.setError()

			val currentState = widgetStateRepositoryImpl.state.first { it.isError }
			assertTrue(currentState.isError)
			assertFalse(currentState.isLoading)
		}

	@Test
	fun `setQuote saves quote and resets loading and error states`() =
		runBlocking {
			val quote =
				Quote(
					text = "The only limit to our realization of tomorrow is our doubts of today.",
					author = "Franklin D. Roosevelt",
					book = "Speeches",
				)

			widgetStateRepositoryImpl.setQuote(quote)

			val currentState = widgetStateRepositoryImpl.state.first { it.quote == quote }
			assertEquals(quote, currentState.quote)
			assertFalse(currentState.isLoading)
			assertFalse(currentState.isError)
		}

	@Test
	fun `setLoading clears error state when previously in error state`() =
		runBlocking {
			widgetStateRepositoryImpl.setError()
			widgetStateRepositoryImpl.state.first { it.isError }

			widgetStateRepositoryImpl.setLoading()

			val currentState = widgetStateRepositoryImpl.state.first { it.isLoading }
			assertTrue(currentState.isLoading)
			assertFalse(currentState.isError)
		}

	@Test
	fun `setError clears loading state when previously in loading state`() =
		runBlocking {
			widgetStateRepositoryImpl.setLoading()
			widgetStateRepositoryImpl.state.first { it.isLoading }

			widgetStateRepositoryImpl.setError()

			val currentState = widgetStateRepositoryImpl.state.first { it.isError }
			assertTrue(currentState.isError)
			assertFalse(currentState.isLoading)
		}

	@Test
	fun `setQuote clears loading state when previously in loading state`() =
		runBlocking {
			val quote =
				Quote(
					text = "Be yourself; everyone else is already taken.",
					author = "Oscar Wilde",
					book = null,
				)

			widgetStateRepositoryImpl.setLoading()
			widgetStateRepositoryImpl.state.first { it.isLoading }

			widgetStateRepositoryImpl.setQuote(quote)

			val currentState = widgetStateRepositoryImpl.state.first { it.quote == quote }
			assertEquals(quote, currentState.quote)
			assertFalse(currentState.isLoading)
			assertFalse(currentState.isError)
		}

	@Test
	fun `setQuote clears error state when previously in error state`() =
		runBlocking {
			val quote = Quote(text = "Stay hungry, stay foolish.", author = "Steve Jobs", book = "Speech")

			widgetStateRepositoryImpl.setError()
			widgetStateRepositoryImpl.state.first { it.isError }

			widgetStateRepositoryImpl.setQuote(quote)

			val currentState = widgetStateRepositoryImpl.state.first { it.quote == quote }
			assertEquals(quote, currentState.quote)
			assertFalse(currentState.isLoading)
			assertFalse(currentState.isError)
		}

	@Test
	fun `setLoading preserves previously saved quote`() =
		runBlocking {
			val quote = Quote(text = "Keep this quote.", author = "Author", book = null)
			widgetStateRepositoryImpl.setQuote(quote)

			widgetStateRepositoryImpl.setLoading()

			val currentState = widgetStateRepositoryImpl.state.first { it.isLoading }
			assertEquals(quote, currentState.quote)
		}

	@Test
	fun `setQuote with null optional fields preserves null author and book`() =
		runBlocking {
			val quote = Quote(text = "To be or not to be.", author = null, book = null)

			widgetStateRepositoryImpl.setQuote(quote)

			val currentState = widgetStateRepositoryImpl.state.first { it.quote == quote }
			assertEquals("To be or not to be.", currentState.quote?.text)
			assertNull(currentState.quote?.author)
			assertNull(currentState.quote?.book)
		}

	@Test
	fun `setQuote uses QuoteConverter to convert quote to json`() =
		runBlocking {
			val fakeConverter = FakeQuoteConverter()
			val localDataStore = WidgetStateRepositoryImpl(fakeDataStore, fakeConverter)
			val quote = Quote(text = "Custom converter quote", author = "Author", book = "Book")

			localDataStore.setQuote(quote)

			val currentState = localDataStore.state.first { it.quote?.text == "parsed" }
			assertEquals(1, fakeConverter.toJsonCallCount)
			assertEquals(quote, fakeConverter.lastQuoteToJson)
			assertEquals("parsed", currentState.quote?.text)
		}
}