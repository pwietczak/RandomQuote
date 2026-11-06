package eu.pw.widget.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentSize
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import eu.pw.widget.R
import eu.pw.widget.contract.WidgetNavigationContract
import eu.pw.widget.data.model.WidgetState
import eu.pw.widget.data.repository.WidgetStateRepositoryImpl
import eu.pw.widget.presentation.component.QuoteWidgetError
import eu.pw.widget.presentation.component.QuoteWidgetLoading
import eu.pw.widget.presentation.component.QuoteWidgetSuccess
import eu.pw.widget.presentation.util.stringResource
import eu.pw.widget.theme.RandomQuoteGlanceTheme

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface WidgetEntryPoint {
	fun quoteDataStore(): WidgetStateRepositoryImpl
}

internal class QuoteWidget : GlanceAppWidget() {
	override suspend fun provideGlance(
		context: Context,
		id: GlanceId,
	) {
		val dataStore =
			EntryPointAccessors
				.fromApplication(
					context.applicationContext,
					WidgetEntryPoint::class.java,
				).quoteDataStore()

		provideContent {
			val state by dataStore.state.collectAsState(initial = WidgetState())
			val stateUi = state.toUi()

			RandomQuoteGlanceTheme {
				QuoteWidgetContent(
					stateUi = stateUi,
				)
			}
		}
	}

	override suspend fun providePreview(
		context: Context,
		widgetCategory: Int,
	) {
		provideContent {
			RandomQuoteGlanceTheme {
				QuoteWidgetContent(
					stateUi = getPreviewState(),
				)
			}
		}
	}
}

@Composable
private fun QuoteWidgetContent(
	stateUi: QuoteWidgetUiState,
	modifier: GlanceModifier = GlanceModifier.wrapContentSize(),
) {
	val context = LocalContext.current
	val intent =
		Intent(WidgetNavigationContract.ACTION_OPEN_APP).apply {
			setPackage(context.packageName)
			flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
		}

	Box(
		modifier =
			modifier
				.appWidgetBackground()
				// .cornerRadius(16.dp) works in api 31+
				.background(ImageProvider(R.drawable.widget_background))
				.clickable(actionStartActivity(intent))
				.padding(RandomQuoteGlanceTheme.spacing.widgetPadding),
		contentAlignment = Alignment.Center,
	) {
		when (stateUi) {
			is QuoteWidgetUiState.Loading -> QuoteWidgetLoading()
			is QuoteWidgetUiState.Error -> QuoteWidgetError(stateUi)
			is QuoteWidgetUiState.Success -> QuoteWidgetSuccess(stateUi)
		}
	}
}

@Composable
private fun getPreviewState(): QuoteWidgetUiState.Success =
	QuoteWidgetUiState.Success(
		quote = stringResource(R.string.wp_quote),
		author = stringResource(R.string.wp_author),
		book = stringResource(R.string.wp_book),
	)

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun QuoteWidgetPreview() {
	val stateUi = getPreviewState()
	RandomQuoteGlanceTheme {
		QuoteWidgetContent(
			stateUi = stateUi,
			modifier =
				GlanceModifier
					.size(width = 260.dp, height = 140.dp)
					.cornerRadius(16.dp),
		)
	}
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun QuoteWidgetDarkPreview() {
	val stateUi = getPreviewState()
	RandomQuoteGlanceTheme(dark = true) {
		QuoteWidgetContent(
			stateUi = stateUi,
			modifier =
				GlanceModifier
					.size(width = 260.dp, height = 140.dp)
					.cornerRadius(16.dp),
		)
	}
}