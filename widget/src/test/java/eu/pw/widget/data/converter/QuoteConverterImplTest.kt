package eu.pw.widget.data.converter

import com.squareup.moshi.JsonDataException
import eu.pw.domain.contract.model.Quote
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

class QuoteConverterImplTest {
	private val converter = QuoteConverterImpl()

	@Test
	fun `toJson converts quote with all fields to json`() {
		val quote =
			Quote(
				text = "The journey of a thousand miles begins with one step.",
				author = "Lao Tzu",
				book = "Tao Te Ching",
			)

		val json = converter.toJson(quote)

		val expectedJson =
			"""{"text":"The journey of a thousand miles begins with one step.","author":"Lao Tzu","book":"Tao Te Ching"}"""
		assertEquals(expectedJson, json)
	}

	@Test
	fun `toJson converts quote with null optional fields to json`() {
		val quote =
			Quote(
				text = "To be or not to be.",
				author = null,
				book = null,
			)

		val json = converter.toJson(quote)

		val expectedJson = """{"text":"To be or not to be."}"""
		assertEquals(expectedJson, json)
	}

	@Test
	fun `toQuote parses valid json string with all fields correctly`() {
		val json =
			"""{"text":"The journey of a thousand miles begins with one step.","author":"Lao Tzu","book":"Tao Te Ching"}"""

		val result = converter.toQuote(json)

		assertEquals("The journey of a thousand miles begins with one step.", result.text)
		assertEquals("Lao Tzu", result.author)
		assertEquals("Tao Te Ching", result.book)
	}

	@Test
	fun `toQuote parses valid json string with missing optional fields correctly`() {
		val json = """{"text":"To be or not to be."}"""

		val result = converter.toQuote(json)

		assertEquals("To be or not to be.", result.text)
		assertNull(result.author)
		assertNull(result.book)
	}

	@Test
	fun `toQuote parses valid json string with explicit null optional fields correctly`() {
		val json = """{"text":"To be or not to be.","author":null,"book":null}"""

		val result = converter.toQuote(json)

		assertEquals("To be or not to be.", result.text)
		assertNull(result.author)
		assertNull(result.book)
	}

	@Test
	fun `toQuote returns empty quote when json is null`() {
		val result = converter.toQuote(null)

		assertEquals("", result.text)
		assertNull(result.author)
		assertNull(result.book)
	}

	@Test
	fun `toJson and toQuote roundtrip preserves full quote object`() {
		val originalQuote =
			Quote(
				text = "Knowledge is power.",
				author = "Francis Bacon",
				book = "Meditationes Sacrae",
			)

		val json = converter.toJson(originalQuote)
		val convertedQuote = converter.toQuote(json)

		assertEquals(originalQuote, convertedQuote)
	}

	@Test
	fun `toJson and toQuote roundtrip preserves quote object with null optional fields`() {
		val originalQuote =
			Quote(
				text = "Think different.",
				author = null,
				book = null,
			)

		val json = converter.toJson(originalQuote)
		val convertedQuote = converter.toQuote(json)

		assertEquals(originalQuote, convertedQuote)
	}

	@Test
	fun `toJson and toQuote roundtrip preserves escaped characters`() {
		val originalQuote =
			Quote(text = "Quotes: \"hello\"\\world\nnext line", author = null, book = null)

		assertEquals(originalQuote, converter.toQuote(converter.toJson(originalQuote)))
	}

	@Test
	fun `toQuote throws when required text field is missing`() {
		assertThrows(JsonDataException::class.java) {
			converter.toQuote("""{"author":"A. Example"}""")
		}
	}
}