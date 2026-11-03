import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

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