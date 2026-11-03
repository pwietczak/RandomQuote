package eu.pw.randomquote.presentation.randomquote.model

import eu.pw.domain.contract.model.Quote
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class QuoteUiTest {
	@Test
	fun `toUi maps quote with all fields present correctly`() {
		val quote =
			Quote(
				text = "The only limit to our realization of tomorrow is our doubts of today.",
				author = "Franklin D. Roosevelt",
				book = "Speeches",
			)

		val result = quote.toUi()

		assertEquals(
			"The only limit to our realization of tomorrow is our doubts of today.",
			result.text,
		)
		assertEquals("Franklin D. Roosevelt", result.author)
		assertEquals("Speeches", result.book)
	}

	@Test
	fun `toUi maps quote with null optional fields correctly`() {
		val quote =
			Quote(
				text = "Knowledge is power.",
				author = null,
				book = null,
			)

		val result = quote.toUi()

		assertEquals("Knowledge is power.", result.text)
		assertNull(result.author)
		assertNull(result.book)
	}

	@Test
	fun `QuoteUi constructor assigns properties correctly`() {
		val quoteUi =
			QuoteUi(
				text = "To be or not to be.",
				author = "William Shakespeare",
				book = "Hamlet",
			)

		assertEquals("To be or not to be.", quoteUi.text)
		assertEquals("William Shakespeare", quoteUi.author)
		assertEquals("Hamlet", quoteUi.book)
	}
}