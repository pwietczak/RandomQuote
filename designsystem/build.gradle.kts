plugins {
	id("randomquote.android.library")
	alias(libs.plugins.kotlin.compose)
}

android {
	namespace = "eu.pw.designsystem"
}

dependencies {
	implementation(platform(libs.androidx.compose.bom))
	api(libs.androidx.compose.material3)
	implementation(libs.androidx.compose.ui.graphics)
}