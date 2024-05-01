package com.smartscore.board.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.smartscore.board.auth.JwtAuthenticationFilter;
import com.smartscore.board.security.jwt.AuthEntryPointJwt;
import com.smartscore.board.security.services.UserDetailsServiceImpl;

import lombok.AllArgsConstructor;

/**
 * https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html
 */
@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final AuthEntryPointJwt unauthorizedHandler;
	private final UserDetailsServiceImpl userDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	private final String[] AUTH_WHITELIST = { "/home", "/auth/login", "/auth/create", "/api/v1/member/login",
			"/api/v1/member/signup",
//			"/api/v1/users",
			"/api/v1/tutorials", // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
			"/api/v1/auth/**", "/api/v1/test/**", "/v3/api-docs/**", "/swagger-ui/**", "/actuator/**", "/v3/api-docs",
			"/v3/api-docs/swagger-config" };

	@Bean
	@Order(0)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		 return
			 http
				.cors( (cors) -> cors
						.configurationSource(apiConfigurationSource()))
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests( (auth) -> auth
						.requestMatchers(AUTH_WHITELIST).permitAll()
						.anyRequest().authenticated())
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		        .exceptionHandling( (exceptionConfig) ->
					exceptionConfig
					.authenticationEntryPoint(unauthorizedHandler)
	//					.accessDeniedHandler(accessDeniedHandler)
				)
		        .userDetailsService(userDetailsService)
		        .build();
	}

//	CorsConfigurationSource apiConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("https://api.example.com"));
//		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}

//	@Bean
	CorsConfigurationSource apiConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.setAllowedOrigins(List.of("http://localhost:3000"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setExposedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
