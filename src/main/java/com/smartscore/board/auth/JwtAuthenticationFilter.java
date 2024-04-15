package com.smartscore.board.auth;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smartscore.board.config.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		log.info("[JwtAuthenticationFilter]");
		Optional<String> authHeader = Optional.ofNullable(request.getHeader("Authorization"));
		log.info("[JwtAuthenticationFilter] authHeader={}", authHeader);
		if (authHeader.isEmpty() || !authHeader.get().startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		log.info("[JwtAuthenticationFilter2]");
		final String jwt = authHeader.get().substring(7);
		final String userId = jwtService.extractUsername(jwt);
		if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			log.info("[JwtAuthenticationFilter3] jwt={}", jwt);
			log.info("[JwtAuthenticationFilter3] userId={}", userId);
			UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
			log.info("[JwtAuthenticationFilter3] userDetails={}", userDetails);


			if (jwtService.isTokenValid(jwt, userDetails)) {
				final var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);

				log.info("[JwtAuthenticationFilter4] authToken={}", authToken);
			}
		}
		log.info("[JwtAuthenticationFilter5]");
		filterChain.doFilter(request, response);
	}
}
