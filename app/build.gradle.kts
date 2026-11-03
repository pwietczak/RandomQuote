plugins {
	id("randomquote.android.application")
	alias(libs.plugins.dagger.hilt.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.kotlin.serialization)
	alias(libs.plugins.ksp)
}

android {
	namespace = "eu.pw.randomquote"

	defaultConfig {
		applicationId = "eu.pw.randomquote"
		targetSdk = 37
		versionCode = 1
		versionName = "1.0"
	}
	buildFeatures {
		compose = true
	}
}

dependencies {
	implementation(project(":domain"))
	implementation(project(":domain:contract"))
	implementation(project(":data"))
	implementation(project(":widget"))
	implementation(project(":widget:contract"))
	implementation(project(":designsystem"))

	implementation(libs.androidx.activity.compose)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.compose.material3)
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.ui.graphics)
	implementation(libs.androidx.compose.ui.tooling.preview)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.splashscreen)
	implementation(libs.timber)

	implementation(libs.hilt)
	implementation(libs.hilt.navigation.compose)
	implementation(libs.hilt.work)
	ksp(libs.hilt.compiler)
	ksp(libs.hilt.compiler.androidx)

	implementation(libs.work)

	testImplementation(libs.junit)

	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.compose.ui.test.junit4)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(libs.androidx.junit)

	debugImplementation(libs.androidx.compose.ui.test.manifest)
	debugImplementation(libs.androidx.compose.ui.tooling)
}