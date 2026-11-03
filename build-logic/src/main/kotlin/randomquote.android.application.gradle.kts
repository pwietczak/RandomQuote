plugins {
	id("com.android.application")
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
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}
}
