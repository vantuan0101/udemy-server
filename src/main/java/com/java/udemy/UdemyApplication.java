package com.java.udemy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.jetbrains.annotations.NotNull;

@SpringBootApplication
public class UdemyApplication {
	@Value(value = "${frontend.root.url}")
	private String FRONTEND_URL;

	public static void main(String[] args) {
		SpringApplication.run(UdemyApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NotNull CorsRegistry registry) {
				registry.addMapping("/**")
						.allowCredentials(true)
						.exposedHeaders("*")
						.maxAge(3600L)
						.allowedOriginPatterns("http://localhost:[*]", FRONTEND_URL)
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
			}
		};
	}

}
