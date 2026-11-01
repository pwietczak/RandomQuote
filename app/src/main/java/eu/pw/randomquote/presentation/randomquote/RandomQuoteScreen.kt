package eu.pw.randomquote.presentation.randomquote

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.pw.randomquote.R
import eu.pw.randomquote.presentation.randomquote.model.QuoteUi
import eu.pw.randomquote.ui.theme.RandomQuoteTheme

@Composable
fun RandomQuoteScreen(
	modifier: Modifier,
	viewModel: RandomQuoteViewModel = hiltViewModel(),
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	RandomQuoteScreenContent(
		modifier = modifier.padding(16.dp),
		uiState = uiState,
		reloadAction = viewModel::loadRandomQuote,
	)
}

@Composable
private fun RandomQuoteScreenContent(
	modifier: Modifier,
	uiState: RandomQuoteUiState,
	reloadAction: () -> Unit,
) {
	Scaffold(
		modifier = modifier,
		bottomBar = {
			RandomQuoteScreenBottomBar(
				uiState = uiState,
				reloadAction = reloadAction,
			)
		},
	) { paddingValues ->
		when (uiState) {
			is RandomQuoteUiState.Loading -> {
				LoadingContent(modifier = Modifier.padding(paddingValues))
			}
			is RandomQuoteUiState.Success -> {
				SuccessContent(modifier = Modifier.padding(paddingValues), quote = uiState.quote)
			}
			is RandomQuoteUiState.Failure -> {
				ErrorContent(modifier = Modifier.padding(paddingValues), messageResId = uiState.messageResId)
			}
		}
	}
}

@Composable
private fun RandomQuoteScreenBottomBar(
	modifier: Modifier = Modifier,
	uiState: RandomQuoteUiState,
	reloadAction: () -> Unit,
) {
	val isLoading = uiState is RandomQuoteUiState.Loading
	val buttonTextRes =
		when (uiState) {
			is RandomQuoteUiState.Success -> R.string.rq_button_reload
			is RandomQuoteUiState.Failure, is RandomQuoteUiState.Loading -> R.string.rq_button_retry
		}

	Button(
		onClick = reloadAction,
		enabled = !isLoading,
		modifier = modifier.fillMaxWidth(),
	) {
		Text(
			text = stringResource(buttonTextRes),
			style = MaterialTheme.typography.labelLarge,
		)
	}
}

@Composable
private fun SuccessContent(
	modifier: Modifier,
	quote: QuoteUi,
) {
	Column(
		modifier = modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		Text(
			text = quote.text,
			style = MaterialTheme.typography.bodyLarge,
			textAlign = TextAlign.Center,
		)
		Spacer(Modifier.height(16.dp))
		quote.author?.let { author ->
			Text(
				text = author,
				style = MaterialTheme.typography.bodyMedium,
			)
		}
		Spacer(Modifier.height(4.dp))
		quote.book?.let { book ->
			Text(
				text = book,
				style = MaterialTheme.typography.bodySmall,
				fontStyle = FontStyle.Italic,
			)
		}
	}
}

@Composable
private fun ErrorContent(
	modifier: Modifier,
	@StringRes messageResId: Int,
) {
	Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		Text(text = stringResource(messageResId), style = MaterialTheme.typography.bodyMedium)
	}
}

@Composable
private fun LoadingContent(modifier: Modifier) {
	Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		CircularProgressIndicator()
	}
}

@PreviewLightDark
@Composable
fun RandomQuoteScreenPreview(
	@PreviewParameter(RandomQuoteUiStateProvider::class) uiState: RandomQuoteUiState,
) {
	RandomQuoteTheme {
		Surface {
			RandomQuoteScreenContent(
				modifier = Modifier.fillMaxSize(),
				uiState = uiState,
				reloadAction = {},
			)
		}
	}
}