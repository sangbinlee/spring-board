package com.smartscore.board.test;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
	public static void main(String[] args) {
		SecretKey key = Jwts.SIG.HS256.key().build();
		log.info("key={}",key);

		String jws = Jwts.builder().subject("Joe").signWith(key).compact();
		log.info("jws={}",jws);

//		SecretKey key2 = Jwts.SIG.HS256.key().build();
//		log.info("key2={}",key2);

		Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload();
		log.info("claims={}",claims);
		log.info("result(Joe)={}",Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().getSubject());
//		assert Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().getSubject().equals("Joe");

//		key=javax.crypto.spec.SecretKeySpec@5880f74
//		jws=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UifQ.GdC43ya4mzohN2-u73jk-Q9IswaBDUX0MHIFAsJIiZY

	}
}
