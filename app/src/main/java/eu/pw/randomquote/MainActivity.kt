package eu.pw.randomquote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import eu.pw.randomquote.ui.theme.RandomQuoteTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen()
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			RandomQuoteTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					MainActivityScaffoldContent(modifier = Modifier.padding(innerPadding))
				}
			}
		}
	}
}

@Composable
private fun MainActivityScaffoldContent(modifier: Modifier) {
	Box(modifier = modifier.fillMaxSize(1f), contentAlignment = Alignment.Center) {
		Text(
			text = "RandomQuote", color = MaterialTheme.colorScheme.onBackground
			)
	}
}

@PreviewLightDark
@Composable
fun MainActivityPreview() {
	RandomQuoteTheme {
		Surface {
			MainActivityScaffoldContent(modifier = Modifier.fillMaxSize())
		}
	}
}
