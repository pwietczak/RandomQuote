package eu.pw.randomquote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import eu.pw.randomquote.presentation.randomquote.RandomQuoteScreen
import eu.pw.randomquote.ui.theme.RandomQuoteTheme
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen()
		Timber.plant(Timber.DebugTree())
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			RandomQuoteTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					RandomQuoteScreen(modifier = Modifier.padding(innerPadding))
				}
			}
		}
	}
}