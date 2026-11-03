plugins {
	id("randomquote.android.optimized-library")
	alias(libs.plugins.dagger.hilt.android)
	alias(libs.plugins.kotlin.serialization)
	alias(libs.plugins.ksp)
}

android {
	namespace = "eu.pw.randomquote.data"
}

dependencies {
	implementation(project(":domain:contract"))

	implementation(libs.kotlin.serialization)
	implementation(libs.kotlin.serialization.json)
	implementation(libs.ktor.client.android)
	implementation(libs.ktor.client.content.negotiation)
	implementation(libs.ktor.serialization.kotlinx.json)
	implementation(libs.timber)

	implementation(libs.hilt)
	ksp(libs.hilt.compiler)
	ksp(libs.hilt.compiler.androidx)

	testImplementation(libs.junit)

	androidTestImplementation(libs.androidx.junit)
}