package com.example.jader.config;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
public class QuerydslConfig {

    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}
}
