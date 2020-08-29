package com.example.blog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests @Autowired constructor(
		val entityManager: TestEntityManager,
		val userRepository: UserRepository,
		val articleRepository: ArticleRepository) {

	@Test
	fun `記事取得（findByIdOrNull）`() {
		val hosomi = User("hosomi", "hosomi", "hosomi2")
		entityManager.persist(hosomi)
		val article = Article("title", "headline", "content", hosomi)
		entityManager.persist(article)
		entityManager.flush()
		val found = articleRepository.findByIdOrNull(article.id!!)
		assertThat(found).isEqualTo(article)
	}

	@Test
	fun `ユーザ取得（findByLogin）`() {
		val hosomi = User("hosomi", "hosomi", "hosomi2")
		entityManager.persist(hosomi)
		entityManager.flush()
		val user = userRepository.findByLogin(hosomi.login)
		assertThat(user).isEqualTo(hosomi)
	}
}
