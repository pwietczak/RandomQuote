plugins {
	id("randomquote.android.optimized-library")
	alias(libs.plugins.dagger.hilt.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.ksp)
}

android {
	namespace = "eu.pw.widget"
}

dependencies {

	implementation(project(":domain:contract"))
	implementation(project(":widget:contract"))
	implementation(project(":designsystem"))

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.ui.tooling.preview)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.datastore.preferences)
	implementation(libs.timber)

	implementation(libs.moshi.kotlin)
	implementation(libs.moshi.adapters)
	ksp(libs.moshi.codegen)

	implementation(libs.hilt)
	implementation(libs.hilt.work)
	ksp(libs.hilt.compiler)
	ksp(libs.hilt.compiler.androidx)

	implementation(libs.work)

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