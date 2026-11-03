package eu.pw.randomquote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import eu.pw.randomquote.presentation.randomquote.RandomQuoteScreen
import eu.pw.randomquote.ui.theme.RandomQuoteTheme
import eu.pw.widget.contract.QuoteWidgetUpdater
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@Inject
	lateinit var quoteWidgetUpdater: QuoteWidgetUpdater

	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen()
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			LaunchedEffect(Unit) {
				quoteWidgetUpdater.updateWidgets()
			}
			RandomQuoteTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					RandomQuoteScreen(modifier = Modifier.padding(innerPadding))
				}
			}
		}
	}
}