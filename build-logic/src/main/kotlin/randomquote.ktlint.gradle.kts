import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
	id("org.jlleitschuh.gradle.ktlint")
}

ktlint {
	android = true
	ignoreFailures = false
	reporters {
		reporter(ReporterType.CHECKSTYLE)
		reporter(ReporterType.HTML)
	}
}

tasks.matching { it.name == "preBuild" }.configureEach {
	dependsOn("ktlintCheck")
}
