package com.smartscore.board.auth;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.smartscore.board.repository.Member;
import com.smartscore.board.repository.MemberDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtService {

	@Value("${jwt.secretString}")
	private String secretString;

	@Value("${jwt.expiration_time}")
	private long expirationTime;

	// private static SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

	private SecretKey getSecretKey() {
		log.info("secretString={}", secretString);
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
			return true;
		} catch (SecurityException e) {
			log.info("Invalid JWT Token 1", e);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT Token 2", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
		}
		return false;
	}

	// JWT Token 을 이용해 memberId 추출
	public Long getMemberId() {
		String accessToken = getJWT();
		log.info("token={}", accessToken);

		// 적당히 여기서 예외처리해도 된다.
		if (accessToken == null) {
			return null;
		}
		if (accessToken.isEmpty()) {
			return null;
		}

		Jws<Claims> jws = Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(accessToken);
		return jws.getPayload()
				.get("memberId", Long.class);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
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

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String GenerateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	public String GenerateTokenById(Long id) {
		Map<String, Object> claims = new HashMap<>();
		return createTokenById(claims, id);
	}

    public String generateToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getSecretKey())
                .compact();
    }
	public String createAccessToken(Member member) {
		// Claims claims = (Claims) Jwts.claims();
		Map<String, Object> claimMap = new HashMap<>();
		claimMap.put("memberId", member.getId());
		claimMap.put("email", member.getEmail());
		// claims.put("role", member.getRole());

		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(expirationTime);

		return Jwts.builder()
				.claims(claimMap)
				// .subject(username)
				.issuedAt(Date.from(now.toInstant()))
				.expiration(Date.from(tokenValidity.toInstant())) // 1분?
				.signWith(getSecretKey())
				.compact();
	}

	public String createAccessToken(MemberDto member) {
		// Claims claims = (Claims) Jwts.claims();
		Map<String, Object> claimMap = new HashMap<>();
		claimMap.put("memberId", member.getId());
		claimMap.put("email", member.getEmail());
		// claims.put("role", member.getRole());

		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(expirationTime);

		return Jwts.builder()
				.claims(claimMap)
				// .subject(username)
				.issuedAt(Date.from(now.toInstant()))
				.expiration(Date.from(tokenValidity.toInstant())) // 1분?
				.signWith(getSecretKey())
				.compact();
	}

	private String createTokenById(Map<String, Object> claims, Long memberId) {

		return Jwts.builder()
				.claims(claims)
				.subject("id로 jwt string 생성")
				.claim("memberId", memberId)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1)) // 1분?
				// .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24L)) //
				// 24시간
				.signWith(getSecretKey())
				.compact();
	}

	/**
	 * https://github.com/sangbinlee/jjwt?tab=readme-ov-file
	 *
	 * @param claims
	 * @param username
	 * @return
	 */
	private String createToken(Map<String, Object> claims, String username) {

		return Jwts.builder()
				.claims(claims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expirationTime)) // 1분?
				// .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1)) // 1분?
				// .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24L)) //
				// 24시간
				.signWith(getSecretKey())
				.compact();
	}

	// Header 에서 JWT Token 추출 "Authorization: {JWT}"
	public String getJWT() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		return request.getHeader("Authorization");
	}
}
