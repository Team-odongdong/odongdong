package com.graduate.odondong.config.Security;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.ClassPathResource;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class CreateAppleClientSecret implements CreateClientSecret{

	public String createClientSecret() {
		Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
		Map<String, Object> jwtHeader = new HashMap<>();
		jwtHeader.put("kid", "T68HJLVXKX");
		jwtHeader.put("alg", "ES256");
		PrivateKey privateKey = null;

		try {
			privateKey = getPrivateKey();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return Jwts.builder()
			.setHeaderParams(jwtHeader)
			.setIssuer("DU84VDSC78")
			.setIssuedAt(new Date(System.currentTimeMillis())) // 발행 시간 - UNIX 시간
			.setExpiration(expirationDate) // 만료 시간
			.setAudience("https://appleid.apple.com")
			.setSubject("site.odongdong.myApp")
			.signWith(SignatureAlgorithm.ES256, privateKey)
			.compact();
	}

	private PrivateKey getPrivateKey() throws IOException {
		ClassPathResource resource = new ClassPathResource("static/AuthKey_T68HJLVXKX.p8");

		InputStream is = resource.getInputStream();
		Reader pemReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		PEMParser pemParser = new PEMParser(pemReader);

		JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
		Object o = pemParser.readObject();
		PrivateKeyInfo object = (PrivateKeyInfo)o;
		return converter.getPrivateKey(object);
	}
}
