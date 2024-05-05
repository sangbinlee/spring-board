package com.smartscore.board.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.smartscore.board.auth.JwtService;
import com.smartscore.board.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

//	@Value("${bezkoder.app.jwtSecret}")
//	private String jwtSecret;

	@Value("${jwt.secretString}")
	private String secretString;

//	@Value("${bezkoder.app.jwtExpirationMs}")
//	private int jwtExpirationMs;

	public String generateJwtToken(UserDetailsImpl userPrincipal) {
		return generateTokenFromUsername(userPrincipal.getUsername());
	}

	private SecretKey getSecretKey() {
		log.info("secretString={}", secretString);
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
	}

	public String generateTokenFromUsername(String username) {
//		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
//				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(getSecretKey()).compact();

		Date nowDate = new Date();
		Date expirationDate = new Date();
//		long expireTime = nowDate.getTime() + 1000 * 60;// 1초 * 60
		long expireTime = nowDate.getTime() + 1000 * 60;// 1초 * 60 * 60 = 60븐 = 1시간
		expirationDate.setTime(expireTime);

		return Jwts.builder()
//                .claims(extraClaims)
				.subject(username).issuedAt(nowDate).expiration(expirationDate)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
				.signWith(getSecretKey()).compact();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		// log.info("result(Joe)={}",Jwts.parser().verifyWith(key).build().psecretKeyms(jws).getPayload().getSubject());

		return Jwts
				.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}


	public String getUserNameFromJwtToken(String token) {
//		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
		return extractClaim(token, Claims::getSubject);
	}

	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
			return true;
		} catch (SecurityException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

}
