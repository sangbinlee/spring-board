package com.smartscore.board.test;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
	public static void main(String[] args) {
		SecretKey key = Jwts.SIG.HS256.key().build();

		String jws = Jwts.builder().subject("Joe").signWith(key).compact();
		log.info("key={}",key);
		log.info("jws={}",jws);

		log.info("result(Joe)={}",Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().getSubject());
//		assert Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().getSubject().equals("Joe");
		
//		key=javax.crypto.spec.SecretKeySpec@5880f74
//		jws=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UifQ.GdC43ya4mzohN2-u73jk-Q9IswaBDUX0MHIFAsJIiZY		
		
	}
}
