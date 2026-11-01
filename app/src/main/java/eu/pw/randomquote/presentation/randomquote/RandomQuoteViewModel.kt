package eu.pw.randomquote.presentation.randomquote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.pw.randomquote.domain.usecase.GetRandomQuoteUseCase
import eu.pw.randomquote.presentation.randomquote.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomQuoteViewModel
	@Inject
	constructor(
		private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
	) : ViewModel() {
		private val _uiState = MutableStateFlow<RandomQuoteUiState>(RandomQuoteUiState.Loading)
		val uiState = _uiState.asStateFlow()

		init {
			loadRandomQuote()
		}

		fun loadRandomQuote() {
			viewModelScope.launch {
				_uiState.value = RandomQuoteUiState.Loading
				when (val result = getRandomQuoteUseCase()) {
					is GetRandomQuoteUseCase.Result.Failure ->
						_uiState.value =
							RandomQuoteUiState.Failure(result.messageResId)
					is GetRandomQuoteUseCase.Result.Success ->
						_uiState.value =
							RandomQuoteUiState.Success(result.quote.toUi())
				}
			}
		}
	}