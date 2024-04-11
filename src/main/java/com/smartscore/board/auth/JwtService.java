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




	private SecretKey getSecretKey() {
		 log.info("secretString={}",secretString);
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
	}


    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException e) {
            log.info("Invalid JWT Token 1", e);
        } catch ( MalformedJwtException e) {
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

//	private static String secretString;

//    public void setSecretString(String jwtSecretString) {
//		JwtService.secretString = jwtSecretString;
//		log.info("jwtSecretString={}", jwtSecretString);
//		log.info("JwtService.secretString={}", JwtService.secretString);
//    }
	// public static final String secretString =
	// "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
	// public static final String secretString =
	// "12345678901234567890123456789012345678901234567890123456789012345678901234567890";
//	private static SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
	// private static SecretKey secretKey =
	// Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

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

	public static void main(String[] args) {
//		log.info("secretKey={}", getSecretKey());
//		 log.info("secretString={}",secretString);
//		 log.info("secretKey={}",secretKey);
		// log.info("secretKey={}",secretKey);
		// log.info("secretKey={}",secretKey);
		// log.info("secretKey={}",secretKey);
		// SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
		// log.info("key={}",key);
		// SecretKey key2 = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
		// log.info("key2={}",key2);
		// SecretKey key3 = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
		// log.info("key3={}",key3);
		// SecretKey key4 = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
		// log.info("key4={}",key4);
		//
		// SecretKey secretKey1 = Jwts.SIG.HS256.key().build();
		// SecretKey secretKey2 = Jwts.SIG.HS256.key().build();
		// SecretKey secretKey3 = Jwts.SIG.HS256.key().build();
		// SecretKey secretKey4 = Jwts.SIG.HS256.key().build();
		// log.info("secretKey1={}",secretKey1);
		// log.info("secretKey2={}",secretKey2);
		// log.info("secretKey3={}",secretKey3);
		// log.info("secretKey4={}",secretKey4);
	}

	// private static SecretKey secretKey = Jwts.SIG.HS256.key().build();

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

	public String createAccessToken(Member member) {
		Claims claims = (Claims) Jwts.claims();
		claims.put("memberId", member.getId());
        claims.put("email", member.getEmail());
//        claims.put("role", member.getRole());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expirationTime);

		return Jwts.builder()
				.claims(claims)
//				.subject(username)
				.issuedAt(Date.from(now.toInstant()))
				.expiration(Date.from(tokenValidity.toInstant())) // 1분?
				.signWith(getSecretKey())
				.compact();
	}

	public String createAccessToken(MemberDto member) {
		Claims claims = (Claims) Jwts.claims();
		claims.put("memberId", member.getId());
        claims.put("email", member.getEmail());
//        claims.put("role", member.getRole());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expirationTime);

		return Jwts.builder()
				.claims(claims)
//				.subject(username)
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
//				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1)) // 1분?
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
