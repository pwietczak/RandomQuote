package eu.pw.data.mapper

import eu.pw.data.remote.dto.QuoteDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class QuoteMapperTest {
	@Test
	fun `toDomain maps all fields correctly when fully populated`() {
		val dto =
			QuoteDto(
				quote = "Be yourself; everyone else is already taken.",
				author = "Oscar Wilde",
				book = "Works",
			)

		val result = dto.toDomain()

		assertEquals("Be yourself; everyone else is already taken.", result.text)
		assertEquals("Oscar Wilde", result.author)
		assertEquals("Works", result.book)
	}

	@Test
	fun `toDomain trims leading and trailing whitespace from fields`() {
		val dto =
			QuoteDto(
				quote = "  Stay hungry, stay foolish.  ",
				author = "  Steve Jobs  ",
				book = "  Stanford Speech  ",
			)

		val result = dto.toDomain()

		assertEquals("Stay hungry, stay foolish.", result.text)
		assertEquals("Steve Jobs", result.author)
		assertEquals("Stanford Speech", result.book)
	}

	@Test
	fun `toDomain maps null optional fields to null`() {
		val dto =
			QuoteDto(
				quote = "Simplicity is prerequisite for reliability.",
				author = null,
				book = null,
			)

		val result = dto.toDomain()

		assertEquals("Simplicity is prerequisite for reliability.", result.text)
		assertNull(result.author)
		assertNull(result.book)
	}

	@Test
	fun `toDomain maps empty optional fields to null`() {
		val dto =
			QuoteDto(
				quote = "Code is read much more often than it is written.",
				author = "",
				book = "",
			)

		val result = dto.toDomain()

		assertEquals("Code is read much more often than it is written.", result.text)
		assertNull(result.author)
		assertNull(result.book)
	}

	@Test
	fun `toDomain maps whitespace-only optional fields to null`() {
		val dto =
			QuoteDto(
				quote = "First, solve the problem. Then, write the code.",
				author = "   ",
				book = " \t\n ",
			)

		val result = dto.toDomain()

		assertEquals("First, solve the problem. Then, write the code.", result.text)
		assertNull(result.author)
		assertNull(result.book)
	}
}