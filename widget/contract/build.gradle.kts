plugins {
	id("randomquote.android.optimized-library")
}

android {
	namespace = "eu.pw.randomquote.widget.contract"
}

dependencies {
	testImplementation(libs.junit)

	androidTestImplementation(libs.androidx.junit)
}