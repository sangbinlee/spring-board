package com.smartscore.board.config;

import org.modelmapper.ModelMapper;
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
import com.smartscore.board.service.CustomUserDetailsService;

import lombok.AllArgsConstructor;

//import com.smartscore.board.auth.JWTAuthenticationEntryPoint;
//import com.smartscore.board.auth.JwtAuthenticationFilter;
@AllArgsConstructor
@Configuration
public class BeanConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
