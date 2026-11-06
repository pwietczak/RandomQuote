package eu.pw.widget.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import eu.pw.domain.contract.model.Quote
import eu.pw.widget.data.converter.QuoteConverter
import eu.pw.widget.data.model.WidgetState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class WidgetStateRepositoryImpl
	@Inject
	constructor(
		private val dataStore: DataStore<Preferences>,
		private val quoteConverter: QuoteConverter,
	) : WidgetStateRepository {
		private companion object {
			const val PREFIX = "quote_widget_"
			const val LOADING_KEY = "${PREFIX}loading"
			const val ERROR_KEY = "${PREFIX}error"
			const val QUOTE_KEY = "${PREFIX}quote"
		}

		private val loadingKey = booleanPreferencesKey(LOADING_KEY)
		private val errorKey = booleanPreferencesKey(ERROR_KEY)
		private val quoteKey = stringPreferencesKey(QUOTE_KEY)

		override val state: Flow<WidgetState> =
			dataStore.data
				.map { preferences ->
					preferences.getState()
				}

		private fun Preferences.readLoading(): Boolean = this[loadingKey] ?: false

		private fun Preferences.readError(): Boolean = this[errorKey] ?: false

		private fun Preferences.readQuote(): String? = this[quoteKey]

		private fun Preferences.getState(): WidgetState {
			val isLoading = readLoading()
			val isError = readError()
			val quote = quoteConverter.toQuote(readQuote())

			return WidgetState(
				isLoading = isLoading,
				quote = quote,
				isError = isError,
			)
		}

		override suspend fun setLoading(): Unit =
			withContext(Dispatchers.IO) {
				dataStore.updateData { preferences ->
					preferences.toMutablePreferences().apply {
						this[loadingKey] = true
						this[errorKey] = false
					}
				}
			}

		override suspend fun setError(): Unit =
			withContext(Dispatchers.IO) {
				dataStore.updateData { preferences ->
					preferences.toMutablePreferences().apply {
						this[errorKey] = true
						this[loadingKey] = false
					}
				}
			}

		override suspend fun setQuote(quote: Quote): Unit =
			withContext(Dispatchers.IO) {
				dataStore.updateData { preferences ->
					preferences.toMutablePreferences().apply {
						this[quoteKey] = quoteConverter.toJson(quote)
						this[loadingKey] = false
						this[errorKey] = false
					}
				}
			}
	}