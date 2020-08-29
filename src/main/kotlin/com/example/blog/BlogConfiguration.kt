package com.example.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
							articleRepository: ArticleRepository) = ApplicationRunner {

        val hosomi = userRepository.save(User("hosomi", "hosomi", "hosomi2"))
        articleRepository.save(Article(
				title = "title",
				headline = "headline",
				content = "content",
				author = hosomi
		))
        articleRepository.save(Article(
				title = "title2",
				headline = "headline2",
				content = "content2",
				author = hosomi
		))
    }
}
