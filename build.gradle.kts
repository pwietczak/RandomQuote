import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	alias(libs.plugins.android.application) apply false
	alias(libs.plugins.dagger.hilt.android) apply (false)
	alias(libs.plugins.kotlin.compose) apply false
	alias(libs.plugins.kotlin.serialization) apply (false)
	alias(libs.plugins.ksp) apply (false)
	alias(libs.plugins.ktlint) apply false
}

allprojects {
	tasks.withType(Test::class).configureEach {
		testLogging {
			events = setOf(
				TestLogEvent.STARTED,
				TestLogEvent.FAILED,
				TestLogEvent.SKIPPED,
				TestLogEvent.PASSED)

			exceptionFormat = TestExceptionFormat.FULL
			showExceptions = true
			showCauses = true
			showStackTraces = true
		}
	}
}