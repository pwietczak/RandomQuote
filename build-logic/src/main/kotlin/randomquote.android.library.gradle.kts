plugins {
	id("com.android.library")
	id("randomquote.ktlint")
}

android {
	compileSdk {
		version = release(37)
	}

	defaultConfig {
		minSdk = 28
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}
}
