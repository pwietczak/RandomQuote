package eu.pw.randomquote.widget.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

internal object QuoteWidgetState {
	val quoteTextKey = stringPreferencesKey("quote")
	val authorKey = stringPreferencesKey("author")
	val bookKey = stringPreferencesKey("book")
	val hasErrorKey = booleanPreferencesKey("has_error")

	val isLoading = booleanPreferencesKey("is_loading")
}