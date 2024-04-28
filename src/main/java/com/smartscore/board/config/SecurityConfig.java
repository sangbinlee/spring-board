package com.smartscore.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smartscore.board.auth.JwtAuthenticationFilter;
import com.smartscore.board.security.jwt.AuthEntryPointJwt;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
//@EnableWebSecurity
public class SecurityConfig {

	private final AuthEntryPointJwt unauthorizedHandler;
	private final UserDetailsServiceImpl userDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final String[] AUTH_WHITELIST = {
			"/home",
			"/auth/login",
			"/auth/create",
			"/api/v1/member/login",
			"/api/v1/member/signup",
			// "/api/v1/**",
			"/api/auth/**",
			"/api/test/**",
			"/v3/api-docs/**",
			"/swagger-ui/**",
			"/actuator/**",
			"/v3/api-docs",
			"/v3/api-docs/swagger-config"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
				.cors(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests( (auth) -> auth
						.requestMatchers(AUTH_WHITELIST).permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
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
