package eu.pw.randomquote.widget.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteDataStore
	@Inject
	constructor(
		private val dataStore: DataStore<Preferences>,
	) {
		private companion object {
			const val PREFIX = "quote_widget_"

			const val LOADING_KEY = "${PREFIX}loading"
			const val ERROR_KEY = "${PREFIX}error"
			const val QUOTE_KEY = "${PREFIX}quote"
		}

		private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
		private val loadingKey = booleanPreferencesKey(LOADING_KEY)
		private val errorKey = booleanPreferencesKey(ERROR_KEY)
		private val quoteKey = stringPreferencesKey(QUOTE_KEY)

		val state =
			dataStore.data
				.map { preferences ->
					preferences.getState().also { state ->
						Timber.d(
							"Quote widget state read: loading=${state.isLoading}, " +
								"error=${state.isError}, hasQuote=${state.quote != null}",
						)
					}
				}.stateIn(
					scope,
					started = SharingStarted.Lazily,
					initialValue = QuoteState(),
				)

		private fun Preferences.readLoading(): Boolean = this[loadingKey] ?: false

		private fun Preferences.readError(): Boolean = this[errorKey] ?: false

		private fun Preferences.readQuote(): String? = this[quoteKey]

		private fun Preferences.getState(): QuoteState {
			val isLoading = readLoading()
			val isError = readError()
			val quote = readQuote()

			return QuoteState(
				isLoading = isLoading,
				quote = quote,
				isError = isError,
			)
		}

		suspend fun setLoading() =
			withContext(Dispatchers.IO) {
				dataStore.updateData { preferences ->
					preferences.toMutablePreferences().apply {
						this[loadingKey] = true
						this[errorKey] = false
					}
				}
			}

		suspend fun setError() =
			withContext(Dispatchers.IO) {
				dataStore.updateData { preferences ->
					preferences.toMutablePreferences().apply {
						this[errorKey] = true
						this[loadingKey] = false
					}
				}
			}

		suspend fun setQuote(quote: String) =
			withContext(Dispatchers.IO) {
				dataStore.updateData { preferences ->
					preferences.toMutablePreferences().apply {
						this[quoteKey] = quote
						this[loadingKey] = false
						this[errorKey] = false
					}
				}
			}
	}