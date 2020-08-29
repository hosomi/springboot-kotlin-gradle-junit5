package com.example.blog

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class HttpControllersTests(@Autowired val mockMvc: MockMvc) {

	@MockkBean
	lateinit var userRepository: UserRepository

	@MockkBean
	lateinit var articleRepository: ArticleRepository

	@Test
	fun `記事一覧`() {
		val hosomi = User("hosomi", "hosomi", "hosomi2")
		val test = Article("test", "headline", "content", hosomi)
		val test2 = Article("test2", "headline2", "content2", hosomi)
		every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(test, test2)
		mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk)
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("\$.[0].author.login").value(hosomi.login))
				.andExpect(jsonPath("\$.[0].slug").value(test.slug))
				.andExpect(jsonPath("\$.[1].author.login").value(hosomi.login))
				.andExpect(jsonPath("\$.[1].slug").value(test2.slug))
	}

	@Test
	fun `ユーザ一覧`() {
		val hosomi = User("hosomi", "hosomi", "hosomi2")
		val alpaca = User("alpaca", "alpaca", "alpaca")
		every { userRepository.findAll() } returns listOf(hosomi, alpaca)
		mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk)
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("\$.[0].login").value(hosomi.login))
				.andExpect(jsonPath("\$.[1].login").value(alpaca.login))
	}
}
