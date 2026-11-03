package eu.pw.domain.contract.error

class QuoteException(
	val error: QuoteError,
	cause: Throwable? = null,
) : Exception(error.name, cause)