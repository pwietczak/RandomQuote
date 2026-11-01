package eu.pw.randomquote.domain.exception

import androidx.annotation.StringRes
import eu.pw.randomquote.R

sealed class LocalizedException(
	@field:StringRes val resId: Int,
) : Exception() {
	class Default(
		resId: Int = R.string.e_default,
	) : LocalizedException(resId)
}