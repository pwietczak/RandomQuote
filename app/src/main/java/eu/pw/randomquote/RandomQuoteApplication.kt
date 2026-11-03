package eu.pw.randomquote

import android.app.Application
import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class RandomQuoteApplication :
	Application(),
	Configuration.Provider {
	@Inject
	lateinit var workerFactory: HiltWorkerFactory

	override fun onCreate() {
		super.onCreate()
		if (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0) {
			Timber.plant(Timber.DebugTree())
		}
	}

	override val workManagerConfiguration: Configuration
		get() =
			Configuration
				.Builder()
				.setWorkerFactory(
					workerFactory,
				).setMinimumLoggingLevel(Log.INFO)
				.build()
}