plugins {
	id("randomquote.android.optimized-library")
	alias(libs.plugins.dagger.hilt.android)
	alias(libs.plugins.ksp)
}

android {
	namespace = "eu.pw.randomquote.domain"
}

dependencies {
	implementation(project(":domain:contract"))
	implementation(libs.timber)

	implementation(libs.hilt)
	ksp(libs.hilt.compiler)
	ksp(libs.hilt.compiler.androidx)

	testImplementation(libs.junit)

	androidTestImplementation(libs.androidx.junit)
}