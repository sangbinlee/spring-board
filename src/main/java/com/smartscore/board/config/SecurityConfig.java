package com.smartscore.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smartscore.board.auth.JwtAuthenticationFilter;
import com.smartscore.board.auth.JwtService;
import com.smartscore.board.service.MemberService;

import lombok.AllArgsConstructor;

//import com.smartscore.board.auth.JWTAuthenticationEntryPoint;
//import com.smartscore.board.auth.JwtAuthenticationFilter;
@AllArgsConstructor
@Configuration
public class SecurityConfig {

	private final JwtService jwtUtil;
	private final MemberService memberService;

	// @Autowired
	// private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	// @Autowired
	// private JwtAuthenticationFilter jwtAuthenticationFilter;
	// @Autowired
//	 private final UserDetailsService userDetailService;
	// @Autowired
	// private PasswordEncoder passwordEncoder;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		final String[] AUTH_WHITELIST = {
				"/home",
				"/auth/login",
				"/auth/create",
				"/api/v1/**",
				"/api/auth/**",
				"/api/test/**",
				"/v3/api-docs/**",
				"/swagger-ui/**",
		};

		http.csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(AUTH_WHITELIST).permitAll()
						.anyRequest().authenticated());
		http.headers(headers -> headers.frameOptions(frameOption -> frameOption.sameOrigin()));
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(new JwtAuthenticationFilter(memberService, jwtUtil), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(memberService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	// PasswordEncoder Bean
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
