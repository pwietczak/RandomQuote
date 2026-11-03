plugins {
	id("randomquote.android.optimized-library")
}

android {
	namespace = "eu.pw.randomquote.domain.contract"
}

dependencies {

	testImplementation(libs.junit)

	androidTestImplementation(libs.androidx.junit)
}