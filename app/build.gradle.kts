import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.dagger.hilt.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.kotlin.serialization)
	alias(libs.plugins.ksp)
	alias(libs.plugins.ktlint)
}

android {
	namespace = "eu.pw.randomquote"
	compileSdk {
		version = release(37)
	}

	defaultConfig {
		applicationId = "eu.pw.randomquote"
		minSdk = 28
		targetSdk = 37
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			optimization {
				enable = true
				packageScope =
					setOf(
						"androidx.**",
						"kotlin.**",
						"kotlinx.**",
					)
			}
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	buildFeatures {
		compose = true
	}

	tasks.getByPath("preBuild").dependsOn("ktlintFormat")
}

dependencies {

	implementation(libs.androidx.activity.compose)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.compose.material3)
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.ui.graphics)
	implementation(libs.androidx.compose.ui.tooling.preview)
	implementation(libs.kotlin.serialization)
	implementation(libs.kotlin.serialization.json)
	implementation(libs.ktor.client.android)
	implementation(libs.ktor.client.content.negotiation)
	implementation(libs.ktor.serialization.kotlinx.json)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.splashscreen)
	implementation(libs.timber)

	implementation(libs.hilt)
	implementation(libs.hilt.navigation.compose)
	implementation(libs.hilt.work)
	ksp(libs.hilt.compiler)
	ksp(libs.hilt.compiler.androidx)

	implementation(libs.work)

	// Uncomment these when using Glance
	implementation(libs.glance.appwidget)
	implementation(libs.glance.appwidget.preview)
	implementation(libs.glance.material3)
	implementation(libs.glance.preview)

	testImplementation(libs.junit)

	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.compose.ui.test.junit4)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(libs.androidx.junit)

	debugImplementation(libs.androidx.compose.ui.test.manifest)
	debugImplementation(libs.androidx.compose.ui.tooling)
}

ktlint {
	android = true
	ignoreFailures = false
	reporters {
		reporter(ReporterType.CHECKSTYLE)
		reporter(ReporterType.HTML)
	}
}