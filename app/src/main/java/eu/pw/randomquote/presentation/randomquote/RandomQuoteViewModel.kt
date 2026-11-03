package eu.pw.randomquote.presentation.randomquote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.pw.domain.contract.error.QuoteError
import eu.pw.domain.contract.usecase.GetRandomQuoteUseCase
import eu.pw.randomquote.R
import eu.pw.randomquote.presentation.randomquote.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomQuoteViewModel
	@Inject
	constructor(
		private val getRandomQuote: GetRandomQuoteUseCase,
	) : ViewModel() {
		private val _uiState = MutableStateFlow<RandomQuoteUiState>(RandomQuoteUiState.Loading)
		val uiState = _uiState.asStateFlow()

		init {
			loadRandomQuote()
		}

		fun loadRandomQuote() {
			viewModelScope.launch {
				_uiState.value = RandomQuoteUiState.Loading
				when (val result = getRandomQuote()) {
					is GetRandomQuoteUseCase.Result.Failure ->
						_uiState.value =
							RandomQuoteUiState.Failure(result.error.toMessageResId())

					is GetRandomQuoteUseCase.Result.Success ->
						_uiState.value =
							RandomQuoteUiState.Success(result.quote.toUi())
				}
			}
		}

		private fun QuoteError.toMessageResId(): Int =
			when (this) {
				QuoteError.NETWORK -> R.string.e_network
				QuoteError.INVALID_RESPONSE -> R.string.e_invalid_response
				QuoteError.GENERAL -> R.string.e_default
			}
	}