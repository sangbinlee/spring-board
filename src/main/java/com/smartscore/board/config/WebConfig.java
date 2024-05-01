package com.smartscore.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
//        registry.addMapping("/**")
        registry.addMapping("/api/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
//                .allowedHeaders(null)
                .allowCredentials(true)
//                .maxAge(3000)//
                .allowedOrigins("*");
    }


//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
////				registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000");
//				registry.addMapping("*").allowedOrigins("*");
//			}
//		};
//	}
}
