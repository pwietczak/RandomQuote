plugins {
	id("randomquote.android.library")
}

android {
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
}
