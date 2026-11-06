package eu.pw.widget.worker

import eu.pw.widget.usecase.QuoteWidgetUpdateUseCase
import javax.inject.Inject

internal class QuoteWidgetWorkRunner
	@Inject
	constructor(
		private val useCase: QuoteWidgetUpdateUseCase,
		private val runtime: QuoteWidgetRuntime,
	) {
		internal enum class Outcome {
			SKIPPED,
			SUCCESS,
			FAILURE,
		}

		internal suspend fun run(): Outcome {
			runtime.publishPreviewIfSupported()
			if (!runtime.hasActiveWidgets()) return Outcome.SKIPPED

			return when (useCase()) {
				QuoteWidgetUpdateUseCase.UpdateResult.Failure -> Outcome.FAILURE
				QuoteWidgetUpdateUseCase.UpdateResult.Success -> Outcome.SUCCESS
			}
		}
	}